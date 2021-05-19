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
    Then status 200
    And match $ == <expected>
    
    Examples:
    |surveyId|expected      |
    |1       |{surveyId:"1"} |
    |null    |{surveyId:null}|

 