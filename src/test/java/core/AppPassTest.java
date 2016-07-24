package core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
// import static org.testng.Assert.assertEquals;
import org.testng.Assert;

public class AppPassTest {

	@BeforeMethod
	public void initialize() {
	} // public void initialize() {

	@DataProvider(name = "test")
	public static String[][] res() {
		return new AppPass().appDrStoD();
	} // public static String[][] res() {

	@Test(dataProvider = "test")
	public void TestResChecker(String acResult, String expResult) {
		Assert.assertEquals(expResult, acResult);
	} // public void TestResChecker(String acResult, String expResult) {
}