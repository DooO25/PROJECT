package pro.s2k.camp.vo;
import javax.xml.bind.annotation.XmlRootElement;

// JSP 페이지 이동시 항상 5개 값은 가지고 넘어가야 한다....
// 그 값을 받아주기 위한 VO
@XmlRootElement
public class CommonVO {

	private int p;
	private int s;
	private int b;
	private int currentPage;
	private int pageSize;
	private int blockSize;
	private int idx;
	private int rv_idx;
	private int qna_idx;
	private int nt_idx;

	private String mode;
	
	public CommonVO() {
		paramCheck();
	}

	private void paramCheck() {
		if(p<1) {
			p = 1;
			currentPage = p;
		}
		if(s<1) {
			s = 10;
			pageSize = s;
		}
		if(b<1) {
			b = 10;
			blockSize = b;
		}
		if(idx<1) {
			idx = 0;
		}
		if(mode==null || mode.trim().length()==0) {
			mode="insert";
		}
	}

	public int getIdx() {
		return idx;
	}
	public int getRv_idx() {
		return rv_idx;
	}
	public int getQna_idx() {
		return qna_idx;
	}
	public int getNt_idx() {
		return nt_idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
		if(idx<1) {
			this.idx = 0;
		}
	}
	public void setRv_idx(int rv_idx) {
		this.rv_idx = rv_idx;
		if(rv_idx<1) {
			this.rv_idx = 0;
		}
	}
	public void setQna_idx(int qna_idx) {
		this.qna_idx = qna_idx;
		if(qna_idx<1) {
			this.qna_idx = 0;
		}
	}
	public void setNt_idx(int nt_idx) {
		this.nt_idx = nt_idx;
		if(nt_idx<1) {
			this.nt_idx = 0;
		}
	}


	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
		if(mode==null || mode.trim().length()==0) {
			this.mode="insert";
		}
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
		if(this.p<1) {
			this.p = 1;
		}
		currentPage = this.p;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
		if(this.s<1) {
			this.s = 10;
		}
		pageSize = this.s;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
		if(this.b<1) {
			this.b = 10;
		}
		blockSize = this.b;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	@Override
	public String toString() {
		return "CommonVO [p=" + p + ", s=" + s + ", b=" + b + ", currentPage=" + currentPage + ", pageSize=" + pageSize
				+ ", blockSize=" + blockSize + ", idx=" + idx + ", rv_idx=" + rv_idx + ", qna_idx=" + qna_idx
				+ ", nt_idx=" + nt_idx + ", mode=" + mode + "]";
	}


}
