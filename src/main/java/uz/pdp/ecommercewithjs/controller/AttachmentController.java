package uz.pdp.ecommercewithjs.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.ecommercewithjs.entity.Attachment;
import uz.pdp.ecommercewithjs.entity.AttachmentContent;
import uz.pdp.ecommercewithjs.repo.AttachmentContentRepository;
import uz.pdp.ecommercewithjs.repo.AttachmentRepository;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class AttachmentController {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentController(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    @PostMapping
    public Integer upload(@RequestParam MultipartFile file) throws IOException {
        Attachment attachment = Attachment
                .builder()
                .fileName(file.getOriginalFilename())
                .build();
        attachmentRepository.save(attachment);
        AttachmentContent attachmentContent = AttachmentContent
                .builder()
                .attachment(attachment)
                .content(file.getBytes())
                .build();
        attachmentContentRepository.save(attachmentContent);

        return attachment.getId();
    }

    @GetMapping("/{attachmentId}")
    public void getFile(@PathVariable Integer attachmentId, HttpServletResponse response) throws IOException {
      AttachmentContent attachmentContent = attachmentContentRepository.findByAttachmentId(attachmentId);
      response.getOutputStream().write(attachmentContent.getContent());
    }
}
