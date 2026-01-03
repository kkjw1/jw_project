package spring.jwProject.domain.inquiry;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;

@Entity
@Getter
public class InquiryAnswer extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "INQUIRY_ANSWER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_INQUIEY_ID")
    private ItemInquiry itemInquiry;
    private String content;
}
