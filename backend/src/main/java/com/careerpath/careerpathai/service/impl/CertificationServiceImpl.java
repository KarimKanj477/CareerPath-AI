package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Certification;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.CertificationNotFoundException;
import com.careerpath.careerpathai.exception.UnauthorizedAccessException;
import com.careerpath.careerpathai.repository.CertificationRepository;
import com.careerpath.careerpathai.service.CertificationService;
import com.careerpath.careerpathai.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final UserService userService;

    public CertificationServiceImpl(CertificationRepository certificationRepository,
                                     UserService userService) {
        this.certificationRepository = certificationRepository;
        this.userService = userService;
    }

    @Override
    public List<Certification> getCertificationsByUserId(Integer userId) {
        return certificationRepository.findByUserId(userId);
    }

    @Override
    public Certification getCertificationById(Integer id) {
        return certificationRepository.findById(id)
                .orElseThrow(() -> new CertificationNotFoundException(
                        "Certification with id " + id + " was not found."));
    }

    @Override
    public Certification createCertification(Integer userId, Certification certification) {
        User user = userService.getUserById(userId);
        certification.setUser(user);
        return certificationRepository.save(certification);
    }

    @Override
    public Certification updateCertification(Integer id, Certification updates, Integer requestingUserId) {
        Certification existing = getCertificationById(id);
        checkOwnership(existing, requestingUserId);

        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getIssuer() != null) existing.setIssuer(updates.getIssuer());
        if (updates.getIssueDate() != null) existing.setIssueDate(updates.getIssueDate());
        if (updates.getExpiryDate() != null) existing.setExpiryDate(updates.getExpiryDate());
        if (updates.getCredentialUrl() != null) existing.setCredentialUrl(updates.getCredentialUrl());

        return certificationRepository.save(existing);
    }

    @Override
    public void deleteCertification(Integer id, Integer requestingUserId) {
        Certification certification = getCertificationById(id);
        checkOwnership(certification, requestingUserId);
        certificationRepository.delete(certification);
    }

    private void checkOwnership(Certification certification, Integer requestingUserId) {
        if (!certification.getUser().getId().equals(requestingUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to modify this certification.");
        }
    }
}
