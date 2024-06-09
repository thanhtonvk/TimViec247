package com.utehy.timviec247.models;

public class UngTuyen {
    private String id;
    private String idCongViec;
    private String idCongTy;
    private String idTaiKhoanUngTuyen;
    private String thoiGian;
    private int trangThai;

    public UngTuyen(String id, String idCongViec, String idCongTy, String idTaiKhoanUngTuyen, String thoiGian, int trangThai) {
        this.id = id;
        this.idCongViec = idCongViec;
        this.idCongTy = idCongTy;
        this.idTaiKhoanUngTuyen = idTaiKhoanUngTuyen;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "UngTuyen{" +
                "id='" + id + '\'' +
                ", idCongViec='" + idCongViec + '\'' +
                ", idCongTy='" + idCongTy + '\'' +
                ", idTaiKhoanUngTuyen='" + idTaiKhoanUngTuyen + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }

    public UngTuyen() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(String idCongViec) {
        this.idCongViec = idCongViec;
    }

    public String getIdCongTy() {
        return idCongTy;
    }

    public void setIdCongTy(String idCongTy) {
        this.idCongTy = idCongTy;
    }

    public String getIdTaiKhoanUngTuyen() {
        return idTaiKhoanUngTuyen;
    }

    public void setIdTaiKhoanUngTuyen(String idTaiKhoanUngTuyen) {
        this.idTaiKhoanUngTuyen = idTaiKhoanUngTuyen;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
