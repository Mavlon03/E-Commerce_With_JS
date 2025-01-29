package uz.pdp.ecommercewithjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommercewithjs.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}