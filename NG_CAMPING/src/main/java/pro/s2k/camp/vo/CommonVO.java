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
   private String mode;
   
   private String searchText;
   private String searchType;
   private String searchType2;
   private String animalCheck;
   
   private double lon;//(mapX)
   private double lat;//(mapY)

   
   public String getSearchType() {
      return searchType;
   }

   public void setSearchType(String searchType) {
      this.searchType = searchType;
   }
   
   public String getSearchType2() {
      return searchType2;
   }
   
   public void setSearchType2(String searchType2) {
      this.searchType2 = searchType2;
   }
   
   public String getSearchText() {
      return searchText;
   }
   
   public void setSearchText(String searchText) {
      this.searchText = searchText;
   }
   
   public String getAnimalCheck() {
      return animalCheck;
   }

   public void setAnimalCheck(String animalCheck) {
      this.animalCheck = animalCheck;
   }
   
   

   public double getLon() {
      return lon;
   }

   public void setLon(double lon) {
      this.lon = lon;
   }

   public double getLat() {
      return lat;
   }

   public void setLat(double lat) {
      this.lat = lat;
   }

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

      if(mode==null || mode.trim().length()==0) {
         mode="insert";
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
            + ", blockSize=" + blockSize + ", mode=" + mode + ", searchText=" + searchText + ", searchType="
            + searchType + ", searchType2=" + searchType2 + ", animalCheck=" + animalCheck + ", lon=" + lon
            + ", lat=" + lat + "]";
   }

}