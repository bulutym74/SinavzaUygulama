package com.example.fethi.sinavzauygulama.diger;

import com.example.fethi.sinavzauygulama.activities.UserInfoItem;

import io.realm.Realm;

public class Islevsel {

    static Realm realm = Realm.getDefaultInstance();

    public static String rootURL = "https://edestek.azurewebsites.net";
    public static String loginURL = rootURL + "/account/loginwithjwt";
    public static String signupURL = rootURL + "/api/account";
    public static String ogrenciTamamlaURL = rootURL + "/api/createOgrenci";
    public static String ogretmenTamamlaURL = rootURL + "/api/createOgretmen";
    public static String profilTamamlaURL = rootURL + "/api/ProfilTamamla";
    public static String isApprovedURL = rootURL + "/api/isApproved";
    public static String yeniKodYollaURL = rootURL + "/api/yeniKodYolla";

    public static String ogrenciOzetURL = rootURL + "/api/ogrenciOzet";
    public static String odevlerURL = rootURL + "/api/odevler";
    public static String odevYollaURL = rootURL + "/api/odevYolla";
    public static String gecikmisOdevlerURL = rootURL + "/api/gecikmisOdevler";
    public static String cozulenKitapYuzdeleriURL = rootURL + "/api/cozulenKitapYuzdeleri";
    public static String ogrenciProfilURL = rootURL + "/api/ogrenciProfil";
    public static String sinifDegistirURL = rootURL + "/api/ogrencisinifDegistir";
    public static String ogrenciSinifYollaURL = rootURL + "/api/ogrenciSinifYolla";
    public static String ogretmenDegistirURL = rootURL + "/api/ogretmenDegistir";
    public static String ogrenciOgretmenYollaURL = rootURL + "/api/ogrenciOgretmenYolla";
    public static String bolumDegistirURL = rootURL + "/api/bolumDegistir";
    public static String sifreDegistirURL = rootURL + "/account/sifreDegistir";
    public static String ogrenciOnayURL = rootURL + "/api/ogrenciOnay";
    public static String sinifDegistirOnayURL = rootURL + "/api/sinifDegistirOnay";

    public static String ogrenciOnayRedURL = rootURL + "/api/ogrenciOnayRed";
    public static String odevVerURL = rootURL + "/api/odevVer";
    public static String danismanOgretmenSiniflarURL = rootURL + "/api/danismanOgretmenSiniflar";
    public static String danismanOgretmenKitaplarURL = rootURL + "/api/danismanOgretmenKitaplar";
    public static String danismanOgretmenKitapEkleURL = rootURL + "/api/danismanOgretmenKitapEkle";
    public static String ogretmenProfilURL = rootURL + "/api/ogretmenProfil";
    public static String ogretmenSinifSilURL = rootURL + "/api/ogretmenSinifSil";
    public static String ogretmenSinifEkleURL = rootURL + "/api/ogretmenSinifEkle";
    public static String ogretmenSinifYollaURL = rootURL + "/api/ogretmenSinifYolla";
    public static String ogretmenSifreDegistirURL = rootURL + "/api/ogretmenSifreDegistir";
    public static String ogretmenSiniflarURL = rootURL + "/api/ogretmenSiniflar";
    public static String kesisenKitaplarURL = rootURL + "/api/kesisenKitaplar";
    public static String danismanKesisenKitaplarURL = rootURL + "/api/danismanKesisenKitaplar";
    public static String sifreSifirlaURL = rootURL + "/account/sifreSifirla";
    public static String bransMenuURL = rootURL + "/api/bransMenu";
    public static String danismanMenuURL = rootURL + "/api/danismanMenu";
    public static String sinifOgrenciOzetURL = rootURL + "/api/sinifOgrenciOzet";
    public static String danismanOgrenciOzetURL = rootURL + "/api/danismanOgrenciOzet";
    public static String odevOnaylaURL = rootURL + "/api/odevOnayla";
    public static String danismanOdevOnaylaURL = rootURL + "/api/danismanOdevOnayla";
    public static String ogrenciCozulenlerURL = rootURL + "/api/ogrenciCozulenler";
    public static String danismanOgrenciCozulenlerURL = rootURL + "/api/danismanOgrenciCozulenler";
    public static String odevOnayRetURL = rootURL + "/api/odevOnayRet";
    public static String danismanOgrenciGecikenlerURL = rootURL + "/api/danismanOgrenciGecikenler";
    public static String ogrenciGecikenlerURL = rootURL + "/api/ogrenciGecikenler";
    public static String danismanVerilenOdevlerURL = rootURL + "/api/danismanVerilenOdevler";
    public static String verilenOdevlerURL = rootURL + "/api/verilenOdevler";
    public static String bransOgretmenKitaplarURL = rootURL + "/api/bransOgretmenKitaplar";
    public static String ogretmenSiniflarOdevOnayURL = rootURL + "/api/ogretmenSiniflarOdevOnay";
    public static String danismanOgretmenSiniflarOdevOnayURL = rootURL + "/api/danismanOgretmenSiniflarOdevOnay";



    public static void updateURL(){

        int id = realm.where(UserInfoItem.class).findAll().get(0).getId();

        ogrenciOzetURL = rootURL + "/api/ogrenciOzet/"+id;
        odevlerURL = rootURL + "/api/odevler/"+id;
        odevYollaURL = rootURL + "/api/odevYolla/"+id;
        gecikmisOdevlerURL = rootURL + "/api/gecikmisOdevler/"+id;
        cozulenKitapYuzdeleriURL = rootURL + "/api/cozulenKitapYuzdeleri/"+id;
        ogrenciProfilURL = rootURL + "/api/ogrenciProfil/"+id;
        sinifDegistirURL = rootURL + "/api/ogrencisinifDegistir/"+id;
        ogrenciSinifYollaURL = rootURL + "/api/ogrenciSinifYolla/"+id;
        ogretmenDegistirURL = rootURL + "/api/ogretmenDegistir/"+id;
        ogrenciOgretmenYollaURL = rootURL + "/api/ogrenciOgretmenYolla/"+id;
        bolumDegistirURL = rootURL + "/api/bolumDegistir/"+id;
        ogrenciOnayURL = rootURL + "/api/ogrenciOnay/"+id;
        sinifDegistirOnayURL = rootURL + "/api/sinifDegistirOnay/"+id;

        ogrenciOnayRedURL = rootURL + "/api/ogrenciOnayRed/"+id;
        odevVerURL = rootURL + "/api/odevVer/"+id;
        danismanOgretmenSiniflarURL = rootURL + "/api/danismanOgretmenSiniflar/"+id;
        danismanOgretmenKitapEkleURL = rootURL + "/api/danismanOgretmenKitapEkle/"+id;
        danismanOgretmenKitaplarURL = rootURL + "/api/danismanOgretmenKitaplar/"+id;
        ogretmenProfilURL = rootURL + "/api/ogretmenProfil/"+id;
        ogretmenSinifSilURL = rootURL + "/api/ogretmenSinifSil/"+id;
        ogretmenSinifEkleURL = rootURL + "/api/ogretmenSinifEkle/"+id;
        ogretmenSinifYollaURL = rootURL + "/api/ogretmenSinifYolla/"+id;
        ogretmenSifreDegistirURL = rootURL + "/api/ogretmenSifreDegistir/"+id;
        ogretmenSiniflarURL = rootURL + "/api/ogretmenSiniflar/"+id;
        kesisenKitaplarURL = rootURL + "/api/kesisenKitaplar/"+id;
        danismanKesisenKitaplarURL = rootURL + "/api/danismanKesisenKitaplar/"+id;
        bransMenuURL = rootURL + "/api/bransMenu/"+id;
        danismanMenuURL = rootURL + "/api/danismanMenu/"+id;
        sinifOgrenciOzetURL = rootURL + "/api/sinifOgrenciOzet/"+id;
        danismanOgrenciOzetURL = rootURL + "/api/danismanOgrenciOzet/"+id;
        odevOnaylaURL = rootURL + "/api/odevOnayla/"+id;
        danismanOdevOnaylaURL = rootURL + "/api/danismanOdevOnayla/"+id;
        ogrenciCozulenlerURL = rootURL + "/api/ogrenciCozulenler/"+id;
        danismanOgrenciCozulenlerURL = rootURL + "/api/danismanOgrenciCozulenler/"+id;
        odevOnayRetURL = rootURL + "/api/odevOnayRet/"+id;
        danismanOgrenciGecikenlerURL = rootURL + "/api/danismanOgrenciGecikenler/"+id;
        ogrenciGecikenlerURL = rootURL + "/api/ogrenciGecikenler/"+id;
        danismanVerilenOdevlerURL = rootURL + "/api/danismanVerilenOdevler/"+id;
        verilenOdevlerURL = rootURL + "/api/verilenOdevler/"+id;
        bransOgretmenKitaplarURL = rootURL + "/api/bransOgretmenKitaplar/"+id;
        ogretmenSiniflarOdevOnayURL = rootURL + "/api/OgretmenSiniflarOdevOnay/"+id;
        danismanOgretmenSiniflarOdevOnayURL = rootURL + "/api/danismanOgretmenSiniflarOdevOnay/"+id;
    }
}
