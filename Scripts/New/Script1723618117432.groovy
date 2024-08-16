import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

TestData data = TestDataFactory.findTestData('Data Files/TestData')


int rowCount = data.getRowNumbers()

WebUI.openBrowser('')

WebUI.navigateToUrl('https://robotsparebinindustries.com/#/robot-order')

WebUI.maximizeWindow()

WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Home/Modal'), 30)

def buttons= [
	findTestObject("Object Repository/Page_Home/buttonModal",['text':'OK']),
	findTestObject("Object Repository/Page_Home/buttonModal",['text':'Yep']),
	findTestObject("Object Repository/Page_Home/buttonModal",['text':'I guess so...']),
	findTestObject("Object Repository/Page_Home/buttonModal",['text':'No way'])
	]
	// Randomly select and click a button
	Random random = new Random()
	int randomIndex = random.nextInt(buttons.size())
	WebUI.click(buttons[randomIndex])
	print("Clicked" + randomIndex + "button")
	if (randomIndex == 3 && WebUI.verifyElementVisible(findTestObject('Object Repository/Page_Home/Modal'), FailureHandling.OPTIONAL)) {
		// If "No way" was clicked and the modal is still visible, select another button randomly except "No way"
		def otherButtons = buttons[0..2] // Exclude "No way" button
		int retryIndex = random.nextInt(otherButtons.size())
		WebUI.click(otherButtons[retryIndex])
	}

//WebUI.click(findTestObject('Object Repository/Page_Home/buttonModal'))

for(def row=1 ; row <=rowCount; row ++) {
	head = data.getValue('Head', row)
	body = data.getValue('Body', row)
	legs= data.getValue('Legs', row)
	address= data.getValue('Address',row)
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Home/select'), head,false)
WebUI.verifyOptionSelectedByValue(findTestObject('Object Repository/Page_Home/select'), head, false, 10)
	 
WebUI.check(findTestObject("Object Repository/Page_Home/radioBody",['id':body]))
WebUI.verifyElementChecked(findTestObject("Object Repository/Page_Home/radioBody",['id':body]), 10)
	  


WebUI.setText(findTestObject("Object Repository/Page_Home/textBox",['placeholder':'Enter the part number for the legs']), legs)
WebUI.setText(findTestObject("Object Repository/Page_Home/textBox",['placeholder':'Shipping address']), address)

}

WebUI.closeBrowser()