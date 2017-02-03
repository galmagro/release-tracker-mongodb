package com.ladbrokes.domain.check.impl;

import com.ladbrokes.domain.check.Check;
import com.ladbrokes.domain.check.CheckDetails;
import com.ladbrokes.domain.check.CheckRepository;
import com.ladbrokes.domain.check.CheckService;
import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.release.BuildDetails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Check service implementation.
 */
@Service
public class CheckServiceBean implements CheckService {

    private CheckRepository checkRepository;

    private int maxHoursHistory;

    @Override
    @Transactional
    public void registerKnownDeployment(EnvironmentDetails targetEnvironment,
                                        BuildDetails registeredBuild,
                                        Date checkTime) {
        Check check = new Check();
        check.setBuildId(new ObjectId(registeredBuild.getId()));
        check.setEnvironmentId(new ObjectId(targetEnvironment.getId()));
        check.setCheckTime(checkTime);
        check.setUnknown(false);
        check.setError(false);
        checkRepository.save(check);

    }

    @Override
    @Transactional
    public void registerUnknownDeployment(EnvironmentDetails targetEnvironment, BuildDetails unallocatedBuild, Date checkTime) {
        Check check = new Check();
        check.setBuildId(new ObjectId(unallocatedBuild.getId()));
        check.setEnvironmentId(new ObjectId(targetEnvironment.getId()));
        check.setCheckTime(checkTime);
        check.setUnknown(true);
        check.setError(false);
        checkRepository.save(check);
    }

    @Override
    @Transactional
    public void registerCheckError(EnvironmentDetails environment, Date checkTime) {
        Check check = new Check();
        check.setEnvironmentId(new ObjectId(environment.getId()));
        check.setCheckTime(checkTime);
        check.setUnknown(false);
        check.setError(true);
        checkRepository.save(check);
    }

    @Override
    public CheckDetails findLastUpdate(String environmentId) {
        ObjectId environmentIdObjId = new ObjectId(environmentId);
        Check lastCheck = checkRepository.findTopByEnvironmentIdOrderByCheckTimeDesc(environmentIdObjId);
        if(lastCheck!=null){
            if(lastCheck.isError()){
                return toCheckDetailsDTO(lastCheck);
            } else if(lastCheck.getBuildId()!=null){
                Check firstDeployedCheck = checkRepository.
                        findTopByEnvironmentIdAndBuildIdOrderByCheckTimeAsc(environmentIdObjId,lastCheck.getBuildId());
                return toCheckDetailsDTO(firstDeployedCheck);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteEnvironmentHistory(String environmentId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1 * maxHoursHistory);
        checkRepository.deleteByEnvironmentIdAndCheckTimeLessThanEqual(new ObjectId(environmentId),calendar.getTime());
    }

    private CheckDetails toCheckDetailsDTO(Check check) {
        CheckDetails checkDetails = new CheckDetails();
        checkDetails.setId(check.getId().toHexString());
        checkDetails.setError(check.isError());
        checkDetails.setBuildId(check.getBuildId().toHexString());
        checkDetails.setEnvironmentId(check.getEnvironmentId().toHexString());
        checkDetails.setTime(check.getCheckTime());

        return checkDetails;
    }

    @Autowired
    public void setCheckRepository(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Value("${check.history.maxhours}")
    public void setMaxHoursHistory(int maxHoursHistory) {
        this.maxHoursHistory = maxHoursHistory;
    }

}
