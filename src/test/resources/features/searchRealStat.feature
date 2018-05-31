Feature: Access corcoran.com website & search a real state for sale
         Using selenium java with cucumber

Scenario Outline: Open StreetEasy WebPage and search real state
    Given I open <paramWeb> webPage
    When Search <Location>, <Custom> for a start value of <Price>
    Then I validate that first element showed is according to criteria <Price>

Examples:
| paramWeb      |Location |Custom|Price     |
| Corcoran      |Manhattan|Yes   |20000000  |