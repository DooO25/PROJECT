package pro.s2k.camp.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadVO {
	private int up_idx;
	private int up_ref;
	private String saveName;
	private String originalName;
}
