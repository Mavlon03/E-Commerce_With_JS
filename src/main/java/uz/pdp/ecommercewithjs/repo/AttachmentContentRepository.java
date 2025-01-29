package uz.pdp.ecommercewithjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommercewithjs.entity.AttachmentContent;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
    AttachmentContent findByAttachmentId(Integer attachmentId);
}