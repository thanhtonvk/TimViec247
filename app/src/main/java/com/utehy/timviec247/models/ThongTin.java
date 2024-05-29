package com.utehy.timviec247.models;

public class ThongTin {
    private String id, diaChi, sdt, email,ngaySinh, gioiThieu,hinhAnh;

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public ThongTin() {
    }

    public ThongTin(String id, String diaChi, String sdt, String email, String ngaySinh, String gioiThieu, String hinhAnh) {
        this.id = id;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiThieu = gioiThieu;
        this.hinhAnh = hinhAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
