package ameiga.saulmm.gdg.data.api.test;

import java.util.List;

import junit.framework.Assert;
import ameiga.saulmm.gdg.data.api.ApiHandler;
import ameiga.saulmm.gdg.data.db.entities.Member;
import android.test.AndroidTestCase;

public class ApiHandlerTest extends AndroidTestCase {
	
	public void testHowToInitializeTheHandler(){
		new ApiHandler(getContext());
	}
	
	public void testGivenTheInitializedHandlerWhenIInvoke_getMembersAndUpdateDB_ThenIExpectToObtainActualTheNumberOfMembersOfGDGVigoCommunityThatIKnowUpToOneUndred(){
		ApiHandler handler = new ApiHandler(getContext());
		
		List<Member> members =handler.getMembersAndUpdateDB();		
		
		Assert.assertTrue(members.size()>100);
		
	}
	
	
//	//HowToTestIt???
//	public void testGivenTheInitializedHandlerWhenIInvoke_getMembersAndUpdateDBANDTheAmmountOfMembersGrows_ThenIExpectToAddTheNewMembersToMyCacheDB(){
//		ApiHandler handler = new ApiHandler(getContext());
//		
//		handler.getMembersAndUpdateDB();
//		
//	}
}
