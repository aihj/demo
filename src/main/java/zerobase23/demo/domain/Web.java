package zerobase23.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Web{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String COUNT;
    private String MGR_NO;
    private String WRDOFC;
    private String MAIN_NM;
    private String ADRES1;
    private String ADRES2;
    private String INSTL_TY;
    private String INSTL_MBY;
    private String SVC_SE;
    private String CMCWR;
    private String CNSTC_YEAR;
    private String INOUT_DOOR;
    private String LNT;
    private String LAT;
}
