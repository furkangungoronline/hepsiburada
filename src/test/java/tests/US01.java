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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import static tests.ReusableMethods.*;

public class US01 extends TestBaseRapor {
    SoftAssert softAssert;
    List<String> begenilenUrunIsimleri;

    @Test
    public void US01() throws IOException {


        HomePage homePage = new HomePage();
        BasePage basePage = new BasePage();
        DetailPage detailPage = new DetailPage();
        LoginPage loginPage = new LoginPage();
        softAssert = new SoftAssert();
        Actions actions = new Actions(Driver.getDriver());

        extentTest = extentReports.createTest("US01_TC01", "Test Caseler sirasiyla otomasyon ile yapılacak.");

        //<http://www.hepsiburada.com/> sitesini açılacak
        MethodPage.anasayfaAc();

        //anasayfanın açıldığını onaylayacak
        Assert.assertTrue(homePage.girisButonu.isDisplayed());
        extentTest.info("anasayfa açıldı");

        waitForPageToLoad(10); // sayfa yüklenene kadar 10 saniye beklenilecek.

        MethodPage.cerezleriKabulEt(); //ekrana gelen çerezleri kabul ettim

        //login ekranını açılacak ve bir kullanıcı ile login olacak
        actions.click(homePage.girisButonu)
                .click(homePage.girisyapButonu).perform();
        login();
        extentTest.info("login sayfası açıldı ve bir kullanıcı ile login olundu");

        //ONCELIKLE BEGENILEN KUTUSUNU BOSALTIYORUZ.
        //MethodPage.begendiklerimKutusunuBosalt();

        //ekranın üstündeki Search alanına 'samsung' yazılacak ve ‘ara' butonuna tıklayacak
        MethodPage.urunArama("samsung");
        extentTest.info("ekranın üstündeki Search alanına 'samsung' yazıldı ve ara butonuna tıklanıldı");

        //sol menüden 'Telefon' sonrasında 'Cep Telefonu' tıklayacak
        basePage.telefonButonu.click();
        //waitForVisibility(basePage.cepTelefonuButonu,10);
        basePage.cepTelefonuButonu.click();
        extentTest.info("sol menüden 'Telefon' sonrasında 'Cep Telefonu' tıklanıldı");

        //gelen sayfada samsung için sonuç bulunduğunu onaylayacak
        String arananKelime = "samsung";
        String actualAramaSonucStr = basePage.samsungSonuc.getText();
        Assert.assertTrue(actualAramaSonucStr.contains(arananKelime));
        extentTest.info("gelen sayfada samsung için sonuç bulunduğu onaylandı");

        //arama sonuçlarından 2. sayfaya tıklanacak
        String secondPageUrl = "sayfa=2";
        String url = Driver.getDriver().getCurrentUrl();
        if (!url.contains(secondPageUrl)) {
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();
        }
        extentTest.info("açılan sayfada 2. sayfanın şu an gösterimde olduğunu onaylandı");

        // TASK'TE HER NE KADAR 2. SAYFAYA TIKLANILACAK STEP'İ BULUNSA DA HEPSİBURADA WEB UYGULAMASININ
        // ARAMA SONUÇLARINDA 2. SAYFA ŞEKLİNDE BİR BUTON BULUNMAMAKTADIR. BU NEDENLE ACTİONS CLASS'I KULLANILARAK
        // PAGE DOWN İLE SAYFANIN URL'İNDE SAYFA=2 YAZISI İÇERENE KADAR PAGE DOWN METHODUNU UYGULADIM. ANCAK AÇILAN
        // BİR SAYFA OLMADIĞI İÇİN İLK SAYFANIN DEVAMI OLDUĞU İÇİN BU KODLARI ÇALIŞTIRMAYIP BİR SONRAKİ STEP'E GEÇTİM.

        //	üstten 5. ürünü tıklayacak
        int i = 5;
        WebElement element = Driver.getDriver().findElement(By.xpath("(//ul[@id='1']/li/div)[" + i + "]"));
        waitForClickablility(element, 10);
        waitFor(3);
        String tiklananUrunIsmi = Driver.getDriver().findElement(By.xpath("(//ul[@id='1']/li/div/a)[" + i + "]")).getAttribute("title");
        actions.scrollToElement(element).click(element).perform();
        extentTest.info("üstten " + i + ". ürüne tıklanıldı");

        //	ÜRÜN FOTOGRAFINA TIKLANILDIĞINDA YENİ SAYFA AÇILDIĞI İÇİN DİĞER SAYFAYA GEÇMEMİZ GEREKİYOR.
        //	BUNUN İÇİN WİNDOW HANDLE DEĞERİNİ ALIP İKİNCİ SAYFAYA GEÇTİM.
        MethodPage.yeniAcilanSayfayaGec();

        //	ürün detayında 'Beğen' butonuna tıklayacak
        MethodPage.begeneTikla();
        extentTest.info("ürün detayında 'Beğen' butonuna tıklanıldı");

        //	’ürün listenize eklendi.' pop up kontrolü yapacak
        MethodPage.popupKontrolEt("Ürün listenize eklendi");
        extentTest.info("’ürün listenize eklendi.' pop up kontrol edildi");

        //	Ekranın en üstündeki hesabım alanında 'Beğendiklerim' tıklayacak
        MethodPage.begendiklerimeTikla();
        extentTest.info("Ekranın en üstündeki hesabım alanında 'Beğendiklerim' tıklandı");

        //	açılan sayfada bir önceki sayfada beğendiklerime alınmış ürünün bulunduğunu onaylayacak
        List<WebElement> begenilenUrunListesi = Driver.getDriver().findElements(By.xpath("//div[@data-test-id='product-card-container']//a"));
        for (int k = 1; k <= begenilenUrunListesi.size(); k++) {
            begenilenUrunIsimleri.add((Driver.getDriver().findElement(By.xpath("(//div[@data-test-id='product-card-container']//a)[" + k + "]")).getAttribute("title")));
        }
        Assert.assertTrue(begenilenUrunIsimleri.contains(tiklananUrunIsmi));
        extentTest.info("açılan sayfada bir önceki sayfada beğendiklerime alınmış ürünün bulunduğu onaylandı");

        //	Beğendiklerime alınmış ürün bulunup seçilecek ve sepete eklenecek
        WebElement begenilenUrunSepeteEkleButonu = Driver.getDriver().findElement(By.xpath("//a[@title='" + tiklananUrunIsmi + "']//div[text()='Sepete ekle']"));
        actions.click(begenilenUrunSepeteEkleButonu).perform();
        extentTest.info("Beğendiklerime alınmış ürün bulunup sepete eklendi");

        //	‘ürün sepete eklendi' pop up kontrolü yapacak
        MethodPage.popupKontrolEt("Ürün sepete eklendi");
        extentTest.info("‘ürün sepete eklendi' pop up kontrolü yapıldı");


        //	Sepetim sayfasına gidecek
        MethodPage.sepetimeTikla();
        extentTest.info("Sepetim sayfasına gidildi");


        // SEPETE EKLENEN ÜRÜNÜN İÇİNE GİRDİĞİMİZDE KALDIR BUTONU BULUNMUYOR BU NEDENLE SEPETİM SEKMESİNDEN
        // ÜRÜNÜN ÜZERİNE GELİP ÇÖP KOVASI İŞARETİ İLE ÜRÜN KALDIRILDI.

        //	Sepete eklenen bu ürünün içine girilip 'Kaldır' butonuna basılacak, sepetimden çıkarılacak
        WebElement sepettenSilinecekUrun = Driver.getDriver().findElement(By.xpath("//a[text()='" + tiklananUrunIsmi + "']//..//..//..//a[@aria-label='Sepetten Çıkar']"));
        actions.moveToElement(sepettenSilinecekUrun);
        actions.click(sepettenSilinecekUrun);
        extentTest.info("Sepete eklenen bu ürünün içine girilip 'Kaldır' butonuna basıldı, sepetimden çıkarıldı");

        // Bu ürünün artık sepette olmadığını onaylayacak
        Assert.assertFalse(sepettenSilinecekUrun.isDisplayed());
        extentTest.pass("Bu ürünün artık sepette olmadığını onaylandı");
        softAssert.assertAll();

        String dosyaYolu = "src/resourses/rapor.xlsx";
        FileInputStream fis = new FileInputStream(dosyaYolu);
        Workbook workbook = WorkbookFactory.create(fis);
        if (!sepettenSilinecekUrun.isDisplayed()) {
            workbook.getSheet("Sayfa1").getRow(0).createCell(1).setCellValue("Test başarı ile sonuçlandırıldı.");
        } else {
            workbook.getSheet("Sayfa1").getRow(0).createCell(1).setCellValue("Test başarısız oldu.");
        }
        FileOutputStream fos = new FileOutputStream(dosyaYolu);
        workbook.write(fos);
        fis.close();
        fos.close();

    }
}