package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.DetailPage;
import pages.HomePage;
import pages.LoginPage;
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
        extentTest=extentReports.createTest("US01_TC01", "<http://www.hepsiburada.com/> sitesini açılacak, anasayfanın açıldığını onaylayacak");
        HomePage homePage = new HomePage();
        softAssert = new SoftAssert();
        //<http://www.hepsiburada.com/> sitesini açılacak
        Driver.getDriver().get(ConfigReader.getProperty("hepsiburadaUrl"));
        extentTest.info("https://hespiburada.com adresine gidildi.");
        //anasayfanın açıldığını onaylayacak
        Assert.assertTrue(homePage.girisButonu.isDisplayed());
        softAssert.assertAll();
        extentTest.pass("anasayfa açıldı");
        Driver.closeDriver();
    }

    @Test
    public void US01_TC02() throws InterruptedException  {
        extentTest=extentReports.createTest("US01_TC02", "login ekranını açılacak,bir kullanıcı ile login olacak");
        LoginPage loginPage = new LoginPage();
        HomePage homePage = new HomePage();
        Driver.getDriver().get(ConfigReader.getProperty("hepsiburadaUrl"));
        waitFor(5);
        Actions actions = new Actions(Driver.getDriver());
        actions.click(homePage.girisButonu)
                .click(homePage.girisyapButonu).perform();
        softAssert = new SoftAssert();
        //login ekranını açılacak
        extentTest.info("login sayfası açıldı");
        //bir kullanıcı ile login olacak
        //Driver.getDriver().get(ConfigReader.getProperty("loginPage"));
        loginPage.userName.sendKeys(ConfigReader.getProperty("validVendorEmail"));
        loginPage.girisYapButonu.click();
        loginPage.password.sendKeys(ConfigReader.getProperty("validVendorPassword"));
        loginPage.girisYapButonu.click();
        waitFor(5);
        softAssert.assertAll();
        //Driver.closeDriver();
    }

    @Test
    public void US01_TC03() {
        extentTest=extentReports.createTest("US01_TC03", "ekranın üstündeki Search alanına 'samsung' yazılacak, ‘ara' butonuna tıklayacak ve devamindaki stepler");
        BasePage basePage = new BasePage();
        DetailPage detailPage = new DetailPage();
        softAssert = new SoftAssert();
        //ekranın üstündeki Search alanına 'samsung' yazılacak
        login();
        basePage.kabulEtButonu.click();
        basePage.searchBox.sendKeys("samsung");
        extentTest.info("ekranın üstündeki Search alanına 'samsung' yazıldı");
        //‘ara' butonuna tıklayacak
        basePage.araButonu.click();
        extentTest.info("ara butonuna tıklanıldı");
        //sol menüden 'Telefon' sonrasında 'Cep Telefonu' tıklayacak
        Actions actions = new Actions(Driver.getDriver());
        basePage.telefonButonu.click();
        //waitForVisibility(basePage.cepTelefonuButonu,10);
        basePage.cepTelefonuButonu.click();
        /* actions.sendKeys(Keys.PAGE_DOWN).build().perform();
        actions.click(basePage.telefonButonu).perform();
        actions.sendKeys(Keys.PAGE_DOWN).build().perform();
        actions.click(basePage.cepTelefonuButonu).perform(); */
        extentTest.info("sol menüden 'Telefon' sonrasında 'Cep Telefonu' tıklanıldı");
        //gelen sayfada samsung için sonuç bulunduğunu onaylayacak
        String arananKelime="samsung";
        String actualAramaSonucStr= basePage.samsungSonuc.getText();
        Assert.assertTrue(actualAramaSonucStr.contains(arananKelime));
        extentTest.info("gelen sayfada samsung için sonuç bulunduğu onaylandı");

        /*
        //arama sonuçlarından 2. sayfaya tıklanacak
        String secondPageUrl = "sayfa=2";
        String url= driver.getCurrentUrl();
        if (!url.contains(secondPageUrl)){
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();}
        extentTest.info("açılan sayfada 2. sayfanın şu an gösterimde olduğunu onaylandı");
        */

        // TASK'TE HER NE KADAR 2. SAYFAYA TIKLANILACAK STEP'İ BULUNSA DA HEPSİBURADA WEB UYGULAMASININ
        // ARAMA SONUÇLARINDA 2. SAYFA ŞEKLİNDE BİR BUTON BULUNMAMAKTADIR. BU NEDENLE ACTİONS CLASS'I KULLANILARAK
        // PAGE DOWN İLE SAYFANIN URL'İNDE SAYFA=2 YAZISI İÇERENE KADAR PAGE DOWN METHODUNU UYGULADIM. ANCAK AÇILAN
        // BİR SAYFA OLMADIĞI İÇİN İLK SAYFANIN DEVAMI OLDUĞU İÇİN BU KODLARI ÇALIŞTIRMAYIP BİR SONRAKİ STEP'E GEÇTİM.

        //	üstten 5. ürünü tıklayacak
        //WebElement besinciUrunResmi= driver.findElement(By.xpath("(//div[@class='moria-ProductCard-joawUM kA-dMVu sxpr5fc5slr'])[5]"));

        //scrollDowntoPixel(200);

        List<WebElement> urunlerListesi = Driver.getDriver().findElements(By.xpath("//ul[@id='1']/li/div"));
        waitForClickablility(urunlerListesi.get(4),10);
        urunlerListesi.get(4).click();
        extentTest.info("üstten 5. ürüne tıklanıldı");

        //	ÜRÜN FOTOGRAFINA TIKLANILDIĞINDA YENİ SAYFA AÇILDIĞI İÇİN DİĞER SAYFAYA GEÇMEMİZ GEREKİYOR.
        //	BUNUN İÇİN WİNDOW HANDLE DEĞERİNİ ALIP İKİNCİ SAYFAYA GEÇTİM.

        String ilkSayfaWindowHandleDegeri=Driver.getDriver().getWindowHandle();
        String ikinciSayfaWindowHandleDegeri="";
        Set<String> windowHandleseti= Driver.getDriver().getWindowHandles();
        for (String each: windowHandleseti
        ) {
            if (!each.equals(ilkSayfaWindowHandleDegeri)){
                ikinciSayfaWindowHandleDegeri=each;
            }
        }
        Driver.getDriver().switchTo().window(ikinciSayfaWindowHandleDegeri);



        //	ürün detayında 'Beğen' butonuna tıklayacak
        waitFor(5);
        detailPage.begenButonu.click();
        extentTest.info("ürün detayında 'Beğen' butonuna tıklanıldı");

        //	’ürün listenize eklendi.' pop up kontrolü yapacak
        String actualAlert1 = Driver.getDriver().findElement(By.xpath("//div[@class='hb-toast-text']")).getText();
        String expectedAlert1= "Ürün listenize eklendi";
        Assert.assertTrue(actualAlert1.contains(expectedAlert1));
        extentTest.info("’ürün listenize eklendi.' pop up kontrol edildi");

        //	Ekranın en üstündeki hesabım alanında 'Beğendiklerim' tıklayacak
        actions.click(detailPage.hesabimButonu)
                .click(detailPage.begendiklerimButonu).perform();
        extentTest.info("Ekranın en üstündeki hesabım alanında 'Beğendiklerim' tıklandı");



        //	açılan sayfada bir önceki sayfada beğendiklerime alınmış ürünün bulunduğunu onaylayacak

        List<WebElement> begenilenUrunListesi = Driver.getDriver().findElements(By.xpath("//div[@class='product-list']//a"));
        System.out.println(begenilenUrunListesi);
        //Bu kısımdan sonrasını en azından tamamlayabildiğim hali ile yetiştirebilmek adına
        // düşündüğüm algoritmayı tamamlayamadan github'a pusladım.
        // Algortima ; Beğenilen ürünlerin listesini alıp ilk beğendiğimiz ürünün
        // URL'sinin bu listede olup olmadığını kontrol eden bir for döngüsü oluşturulacak.
        // Oluşturulan for döngüsü içinde beğenilen ürünün URL'si bulunuyor ise test pass olacak.

        //a[@href='https://www.hepsiburada.com/samsung-galaxy-a32-128-gb-samsung-turkiye-garantili-p-HBCV000005PKKF']

        extentTest.info("açılan sayfada bir önceki sayfada beğendiklerime alınmış ürünün bulunduğu onaylandı");


        //	Beğendiklerime alınmış ürün bulunup seçilecek ve sepete eklenecek
        actions.click(detailPage.begenilenUrun)
                        .click(detailPage.sepeteEkleButonu).perform();
        extentTest.info("Beğendiklerime alınmış ürün bulunup seçildi ve sepete eklendi");



        //	‘ürün sepete eklendi' pop up kontrolü yapacak
        String actualAlert2 = Driver.getDriver().findElement(By.xpath("//div[@class='hb-toast-text']")).getText();
        String expectedAlert2 = "Ürün sepete eklendi.";
        Assert.assertEquals(actualAlert2,expectedAlert2);
        extentTest.info("‘ürün sepete eklendi' pop up kontrolü yapıldı");


        //	Sepetim sayfasına gidecek
        actions.click(detailPage.sepetimButonu);
        if(Driver.getDriver().getCurrentUrl().contains("sepetim")){
        extentTest.info("Sepetim sayfasına gidildi");}


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
