package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.Certification;

import java.util.List;

public interface CertificationService {
    List<Certification> getCertificationsByUserId(Integer userId);
    Certification getCertificationById(Integer id);
    Certification createCertification(Integer userId, Certification certification);
    Certification updateCertification(Integer id, Certification updates, Integer requestingUserId);
    void deleteCertification(Integer id, Integer requestingUserId);
}
