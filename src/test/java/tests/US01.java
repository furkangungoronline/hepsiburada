package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

import java.util.List;
import java.util.Set;

import static tests.ReusableMethods.*;

public class US01 extends TestBaseRapor {
    SoftAssert softAssert;

    @Test
    public void US01_TC01() {

        HomePage homePage = new HomePage();
        BasePage basePage = new BasePage();
        DetailPage detailPage = new DetailPage();
        LoginPage loginPage = new LoginPage();
        softAssert = new SoftAssert();
        Actions actions = new Actions(Driver.getDriver());

        extentTest=extentReports.createTest("US01_TC01", "Test Caseler sirasiyla otomasyon ile yapılacak.");

        //<http://www.hepsiburada.com/> sitesini açılacak
        MethodPage.AnasayfaAc();

        //anasayfanın açıldığını onaylayacak
        Assert.assertTrue(homePage.girisButonu.isDisplayed());
        extentTest.info("anasayfa açıldı");

        waitForPageToLoad(10); // sayfa yüklenene kadar 10 saniye beklenilecek.

        //login ekranını açılacak ve bir kullanıcı ile login olacak
        actions.click(homePage.girisButonu)
                .click(homePage.girisyapButonu).perform();
        login();
        extentTest.info("login sayfası açıldı ve bir kullanıcı ile login olundu");

        //ONCELIKLE BEGENILEN KUTUSUNU BOSALTIYORUZ.
        MethodPage.BegendiklerimKutusunuBosalt();

        MethodPage.CerezleriKabulEt(); //ekrana gelen çerezleri kabul ettim

        //ekranın üstündeki Search alanına 'samsung' yazılacak ve ‘ara' butonuna tıklayacak
        MethodPage.SamsungUrunArama();
        extentTest.info("ekranın üstündeki Search alanına 'samsung' yazıldı ve ara butonuna tıklanıldı");

        //sol menüden 'Telefon' sonrasında 'Cep Telefonu' tıklayacak
        basePage.telefonButonu.click();
        //waitForVisibility(basePage.cepTelefonuButonu,10);
        basePage.cepTelefonuButonu.click();

        extentTest.info("sol menüden 'Telefon' sonrasında 'Cep Telefonu' tıklanıldı");
        //gelen sayfada samsung için sonuç bulunduğunu onaylayacak
        String arananKelime="samsung";
        String actualAramaSonucStr= basePage.samsungSonuc.getText();
        Assert.assertTrue(actualAramaSonucStr.contains(arananKelime));
        extentTest.info("gelen sayfada samsung için sonuç bulunduğu onaylandı");


        //arama sonuçlarından 2. sayfaya tıklanacak
        String secondPageUrl = "sayfa=2";
        String url= Driver.getDriver().getCurrentUrl();
        if (!url.contains(secondPageUrl)){
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();}
        extentTest.info("açılan sayfada 2. sayfanın şu an gösterimde olduğunu onaylandı");

        // TASK'TE HER NE KADAR 2. SAYFAYA TIKLANILACAK STEP'İ BULUNSA DA HEPSİBURADA WEB UYGULAMASININ
        // ARAMA SONUÇLARINDA 2. SAYFA ŞEKLİNDE BİR BUTON BULUNMAMAKTADIR. BU NEDENLE ACTİONS CLASS'I KULLANILARAK
        // PAGE DOWN İLE SAYFANIN URL'İNDE SAYFA=2 YAZISI İÇERENE KADAR PAGE DOWN METHODUNU UYGULADIM. ANCAK AÇILAN
        // BİR SAYFA OLMADIĞI İÇİN İLK SAYFANIN DEVAMI OLDUĞU İÇİN BU KODLARI ÇALIŞTIRMAYIP BİR SONRAKİ STEP'E GEÇTİM.

        //	üstten 5. ürünü tıklayacak
        List<WebElement> urunlerListesi = Driver.getDriver().findElements(By.xpath("//ul[@id='1']/li/div"));
        waitForClickablility(urunlerListesi.get(4),20);
        urunlerListesi.get(4).click();
        extentTest.info("üstten 5. ürüne tıklanıldı");

        //	ÜRÜN FOTOGRAFINA TIKLANILDIĞINDA YENİ SAYFA AÇILDIĞI İÇİN DİĞER SAYFAYA GEÇMEMİZ GEREKİYOR.
        //	BUNUN İÇİN WİNDOW HANDLE DEĞERİNİ ALIP İKİNCİ SAYFAYA GEÇTİM.
        MethodPage.YeniAcilanSayfayaGec();

        //	ürün detayında 'Beğen' butonuna tıklayacak
        MethodPage.BegeneTikla();
        extentTest.info("ürün detayında 'Beğen' butonuna tıklanıldı");

        //	’ürün listenize eklendi.' pop up kontrolü yapacak
        MethodPage.PopupKontrolEt("ürün listenize eklendi");
        extentTest.info("’ürün listenize eklendi.' pop up kontrol edildi");

        //	Ekranın en üstündeki hesabım alanında 'Beğendiklerim' tıklayacak
        MethodPage.BegendiklerimeTikla();
        extentTest.info("Ekranın en üstündeki hesabım alanında 'Beğendiklerim' tıklandı");

        //	açılan sayfada bir önceki sayfada beğendiklerime alınmış ürünün bulunduğunu onaylayacak
        List<WebElement> begenilenUrunListesi = Driver.getDriver().findElements(By.xpath("//div[@class='product-list']//a"));
        System.out.println(begenilenUrunListesi);
        extentTest.info("açılan sayfada bir önceki sayfada beğendiklerime alınmış ürünün bulunduğu onaylandı");


        //	Beğendiklerime alınmış ürün bulunup seçilecek ve sepete eklenecek
        actions.click(detailPage.begenilenUrun)
                        .click(detailPage.sepeteEkleButonu).perform();
        extentTest.info("Beğendiklerime alınmış ürün bulunup seçildi ve sepete eklendi");



        //	‘ürün sepete eklendi' pop up kontrolü yapacak
        MethodPage.PopupKontrolEt("ürün sepete eklendi");
        extentTest.info("‘ürün sepete eklendi' pop up kontrolü yapıldı");


        //	Sepetim sayfasına gidecek
        MethodPage.SepetimeTikla();
        extentTest.info("Sepetim sayfasına gidildi");


        // SEPETE EKLENEN ÜRÜNÜN İÇİNE GİRDİĞİMİZDE KALDIR BUTONU BULUNMUYOR BU NEDENLE SEPETİM SEKMESİNDEN
        // ÜRÜNÜN ÜZERİNE GELİP ÇÖP KOVASI İŞARETİ İLE ÜRÜN KALDIRILDI.

        //	Sepete eklenen bu ürünün içine girilip 'Kaldır' butonuna basılacak, sepetimden çıkarılacak
        actions.moveToElement(detailPage.begenilenUrun);
        actions.click(detailPage.kaldirmaButonu);
        extentTest.info("Sepete eklenen bu ürünün içine girilip 'Kaldır' butonuna basıldı, sepetimden çıkarıldı");



        // Bu ürünün artık sepette olmadığını onaylayacak

        // Algortima ; Sepetin tamamen boş olduğu yada eklenen ürünün URL'sinin sepetteki ürünlerin listesinin içinde olup olmadığını
        // kontrol eden bir for döngüsü oluşturulacak. Eğer for döngüsü bize boş dönerse artık o ürün sepetten kaldırılmış
        // olacak ve test pass olacak.


        extentTest.pass("Bu ürünün artık sepette olmadığını onaylandı");
        softAssert.assertAll();

    }
}
