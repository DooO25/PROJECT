package pro.s2k.camp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadVO {
	private int idx;
	private int ref;
	private String saveName;
	private String oriName;
}
