#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: Reports
 Background:
        * url 'http://localhost:8080'

  Scenario Outline: surveyId:<surveyId>
    Given path 'reports'
    And params {surveyId:'#(surveyId)'}
    When method GET
    Then status <status>
    And match $.surveyId == "2"
    And match $.weekEnum == '#null'
    
    Examples:
    |surveyId|status|expected                           |
    |1       |200   |{surveyId:"1", weekEnum:'#null'}   |

  Scenario Outline: surveyId:<surveyId>
    Given path 'reports'
    And params {surveyId:'#(surveyId)'}
    When method GET
    Then status <status>
    
    Examples:
    |surveyId|status|
    |1       |200   |
    |        |400   |
 

  Scenario Outline: surveyId:<surveyId> weekEnum:<weekEnum>
    Given path 'reports'
    And params {surveyId:'#(surveyId)', weekEnum:'#(weekEnum)'}
    When method GET
    Then status <status>
    And match $ == <expected>
    
    Examples:
    |surveyId|weekEnum|status|expected                         |
    |1       |1       |200   |{surveyId:"1", weekEnum:"1"}     |
 