package antne.imagekeeper.resourceserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The type Image info.
 * <p>
 * The main class for images, which is stored in the database and operations are performed
 * on it in the form of adding, deleting and searching images
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ImageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uniq_phrase")
    private String uniqPhrase;

    @ElementCollection
    @Column(name = "key_phrase")
    private String[] keysPhrase;

    @Column(name = "path_to_image")
    private String pathToImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
