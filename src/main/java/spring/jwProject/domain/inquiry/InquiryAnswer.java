package spring.jwProject.domain.inquiry;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;

@Entity
@Getter
public class InquiryAnswer extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "INQUIRY_ANSWER_NO")
    private Long no;

    @ManyToOne
    @JoinColumn(name = "ITEM_INQUIEY_NO")
    private ItemInquiry itemInquiry;
    private String content;
}
