package spring.jwProject.domain.inquiry;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.item.Item;

@Entity
@Getter
public class ItemInquiry extends BaseEntity {
    @Id @GeneratedValue
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
