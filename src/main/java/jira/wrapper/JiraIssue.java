package jira.wrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

public class JiraIssue {
	
	//Test the method
	public static void main(String[] args) throws UnsupportedOperationException, IOException, URISyntaxException {

		createJiraIssue("AP", "Test issue", "Test issue through API", "Bug");
	}

	/*
	 * This method creates a JIRA issue based on the parameters passed to it
	 */
	public static void createJiraIssue(String project, String summary, String description, String issueTypeName)
			throws FileNotFoundException, MalformedURLException, IOException, URISyntaxException {
		Scanner scanner = new Scanner(new File("src/main/resources/jiraissueformat.json"));
			String content = scanner.useDelimiter("\\Z").next().replaceAll("\n", "");
			String formattedJson = new MessageFormat(content).format(new String[]{ project, summary, description, issueTypeName});

			
			AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
	        @SuppressWarnings("deprecation")
	        URI jiraServerUri = new URI("https://hikeapp.atlassian.net/");
	        BasicHttpAuthenticationHandler objBasicHttpAuthenticationHandler = new BasicHttpAuthenticationHandler("replace-username", "replace-password");
	        final JiraRestClient restClient = factory.create(jiraServerUri, objBasicHttpAuthenticationHandler);
	         try
	         {
	        	 IssueInputBuilder issueInputBuilder = new IssueInputBuilder(project, 10011l);
	        	 issueInputBuilder.setSummary(summary);
	        	 issueInputBuilder.setDescription(description);	        	 
	        Promise<BasicIssue> createIssue = restClient.getIssueClient().createIssue(issueInputBuilder.build());
	        BasicIssue basicIssue = createIssue.get();
	        System.out.println(basicIssue.toString());
	         }
	         catch (RestClientException ex)
	         {
	                 System.out.println(ex.getMessage());
	                 System.out.println(ex.getCause());
	         } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       
	}

}
