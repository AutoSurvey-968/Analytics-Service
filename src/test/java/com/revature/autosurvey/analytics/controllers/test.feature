Feature: Quote generator
 
    Background:
        * url 'http://localhost:8080'
    
    Scenario: Fetch reports
    
        Given path 'reports'
        When method GET
        Then status 200