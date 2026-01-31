package spring.jwProject.domain.inquiry;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.item.Item;

@Entity
@Getter
@SequenceGenerator(name = "ITEM_INQUIRY_SEQ", sequenceName = "ITEM_INQUIRY_SEQ", initialValue = 1, allocationSize = 1)
public class ItemInquiry extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_INQUIRY_SEQ")
    @Column(name = "ITEM_INQUIRY_NO")
    private Long no;

    @ManyToOne
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    private Long memberId;
    private String title;
    private String content;
    @Lob
    private String image;
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;
}
