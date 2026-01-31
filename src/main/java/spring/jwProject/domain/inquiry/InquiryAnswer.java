package spring.jwProject.domain.inquiry;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;

@Entity
@Getter
@SequenceGenerator(name = "INQUIRY_ANSWER_SEQ", sequenceName = "INQUIRY_ANSWER_SEQ", initialValue = 1, allocationSize = 1)
public class InquiryAnswer extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INQUIRY_ANSWER_SEQ")
    @Column(name = "INQUIRY_ANSWER_NO")
    private Long no;

    @ManyToOne
    @JoinColumn(name = "ITEM_INQUIEY_NO")
    private ItemInquiry itemInquiry;
    private String content;
}
