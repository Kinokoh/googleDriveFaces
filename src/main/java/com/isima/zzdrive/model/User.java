/**
 * ZZDrive - 2014
 *
 * @author Arnaud CHALIEZ
 * @author Jérémy BOUNY
 */
package com.isima.zzdrive.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.primefaces.util.Base64;

@Entity
@Table(name = "user", catalog = "zzdrive", uniqueConstraints = @UniqueConstraint(columnNames = "username")
)
public class User implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser")
    private int iduser;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "localecode")
    private String localeCode;

    @Column(name = "homeid", nullable = true)
    private Integer homeid;

    public Integer getHomeid() {
        return homeid;
    }

    public void setHomeid(Integer homeid) {
        this.homeid = homeid;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "userrole", catalog = "zzdrive", joinColumns = {
        @JoinColumn(name = "iduser", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idrole", nullable = false, updatable = false)}
    )
    private Set<Role> roles = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Access> accesses = new HashSet<>(0);

    public User() {
    }

    public User(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;
        this.password = computeHash(password);
    }

    public User(String username, String firstname, String lastname, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = computeHash(password);
        this.roles = new HashSet<>();
        this.accesses = new HashSet<>();
    }

    public User(String username, String firstname, String lastname, String password, Set<Role> roles, Set<Access> accesses) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = computeHash(password);
        this.roles = roles;
        this.accesses = accesses;
    }

    public void setPasswordToHash(String passwordPlaintext) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.setPassword(this.computeHash(passwordPlaintext));
    }

    public boolean checkPasswordForLogin(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (null == password || password.isEmpty()) {
            return false;
        }
        return this.getPassword().equals(this.computeHash(password));
    }

    private String computeHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[]{42}, 2048, 160);
        SecretKeyFactory fact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return Base64.encodeToString(fact.generateSecret(spec).getEncoded(), false);
    }

    public int getIduser() {
        return iduser;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Access> getAccesses() {
        return accesses;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }
}
