package propensi.a04.wois.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "admin")
public class AdminModel extends UserModel implements Serializable  {

}
