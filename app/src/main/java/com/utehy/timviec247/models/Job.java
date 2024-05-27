package com.utehy.timviec247.models;

import android.widget.EditText;

public class Job {
    private String id;
    private String viTri;
    private int luongMin, luongMax;
    private String diaChi;
    private int kinhNghiem;
    private String hinhThuc;
    private int soLuong;
    private String gioiTinh;
    private int thoiGian;
    private String moTa;
    private String yeuCau, quyenLoi, thoiGianLamViec;
    private String capBac;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Job() {
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public int getLuongMin() {
        return luongMin;
    }

    public void setLuongMin(int luongMin) {
        this.luongMin = luongMin;
    }

    public int getLuongMax() {
        return luongMax;
    }

    public void setLuongMax(int luongMax) {
        this.luongMax = luongMax;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getKinhNghiem() {
        return kinhNghiem;
    }

    public void setKinhNghiem(int kinhNghiem) {
        this.kinhNghiem = kinhNghiem;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(int thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public String getQuyenLoi() {
        return quyenLoi;
    }

    public void setQuyenLoi(String quyenLoi) {
        this.quyenLoi = quyenLoi;
    }

    public String getThoiGianLamViec() {
        return thoiGianLamViec;
    }

    public void setThoiGianLamViec(String thoiGianLamViec) {
        this.thoiGianLamViec = thoiGianLamViec;
    }

    public String getCapBac() {
        return capBac;
    }

    public void setCapBac(String capBac) {
        this.capBac = capBac;
    }

    public Job(String viTri, int luongMin, int luongMax, String diaChi, int kinhNghiem, String hinhThuc, int soLuong, String gioiTinh, int thoiGian, String moTa, String yeuCau, String quyenLoi, String thoiGianLamViec, String capBac) {
        this.viTri = viTri;
        this.luongMin = luongMin;
        this.luongMax = luongMax;
        this.diaChi = diaChi;
        this.kinhNghiem = kinhNghiem;
        this.hinhThuc = hinhThuc;
        this.soLuong = soLuong;
        this.gioiTinh = gioiTinh;
        this.thoiGian = thoiGian;
        this.moTa = moTa;
        this.yeuCau = yeuCau;
        this.quyenLoi = quyenLoi;
        this.thoiGianLamViec = thoiGianLamViec;
        this.capBac = capBac;
    }
}
