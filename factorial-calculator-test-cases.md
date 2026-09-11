# Factorial Calculator — Test Cases & Defects

Full narrative report with evidence screenshots: [`QA_Test_Report_Factorial_Calculator.docx`](../QA_Test_Report_Factorial_Calculator.docx). This file is the test case table and defect list on their own, for a quick scan.

**Target:** http://qainterview.pythonanywhere.com/ · **Coverage:** 23/23 planned cases (100%) · **Pass rate:** 9/22 binary-outcome cases (40.9%) · **Defects:** 9 (2 Critical, 1 High, 6 Medium)

## Test cases

| ID | Test Case | Input | Expected | Actual | Status |
|---|---|---|---|---|---|
| FUNC-01 | Valid factorial | 5 | 120 | 120 | Pass |
| FUNC-02 | Valid factorial | 10 | 3,628,800 | 3628800 | Pass |
| FUNC-03 | Zero factorial | 0 | 1 | 1 | Pass |
| FUNC-04 | Factorial of one | 1 | 1 | 1 | Pass |
| FUNC-05 | Large number precision | 33 | Exact 37-digit integer | Scientific notation, precision loss | Fail |
| FUNC-06 | Large number precision | 50 | Exact 65-digit integer | Scientific notation, precision loss | Fail |
| FUNC-07 | Large number precision | 100 | Exact 158-digit integer | Scientific notation, precision loss | Fail |
| FUNC-08 | Overflow threshold | 171 / 345 | Infinity accepted per FAQ, but reference calculators still return exact finite value at this magnitude | Infinity returned earlier than mathematically necessary | Pass (with observation) |
| FUNC-09 | Negative number handling | -1 / -89 | Blocked or clear message | No UI message; backend returns 500 | Fail |
| FUNC-10 | Large valid positive integer | 1000 | Correct result or graceful error | Silent failure — no UI message, backend returns 500 | Fail |
| FUNC-11 | Decimal input | 2.5 | Rejected, submission blocked | Message shown, but POST still sent | Fail |
| FUNC-12 | Non-numeric text | "abc" / "@@@" | Rejected with clear message | Rejected correctly, no request sent | Pass |
| FUNC-13 | Empty field submission | (empty) | Clear message, no crash | Message shown; console shows handler still processes blank value first | Pass (minor note) |
| FUNC-14 | Submit via Enter key | 7 + Enter | Same as clicking Calculate | Nothing happens, no errors logged | Fail |
| FUNC-15 | Rapid repeated clicks | Multiple clicks (value 50) | Button disabled or loading state | Multiple simultaneous requests, no debounce | Fail |
| FUNC-16 | Mobile viewport responsiveness | Galaxy A51/71 emulation | Fully usable, proportional layout | Responsive overall, but footer links small/close, title doesn't scale | Pass (with notes) |
| FUNC-17 | Extremely long numeric input | Long pasted digit string | Reasonable max length enforced, or fast clear rejection | No limit enforced; repeated 500s and instability | Fail |
| API-01 | Valid POST request | number=5 | 200 OK, {"answer":120} | 200 OK, {"answer":120} | Pass |
| API-02 | Non-numeric value via direct POST | number=teste | 400 Bad Request | 500 Internal Server Error | Fail |
| API-03 | Missing/incorrect parameter name | CHAVE=5 | 400 Bad Request | 500 Internal Server Error | Fail |
| API-04 | HEAD request to root URL | curl -Iv | 200 OK with headers | 500 Internal Server Error | Fail |
| SEC-01 | Security response headers | Inspect headers | HSTS, X-Frame-Options / CSP present | None of the standard headers found | Fail |
| SEC-02 | Direct endpoint access (bypass UI) | POST directly via curl | Architecture observation | Confirmed possible — enables API-02/API-03 | Confirmed |

## Defects

| ID | Title | Severity | Priority |
|---|---|---|---|
| BUG-01 | Backend returns unhandled 500 instead of 400 for invalid API requests | Critical | P1 |
| BUG-02 | Silent failures with no user-facing error for negative numbers and certain large valid integers | Critical | P1 |
| BUG-03 | Server crashes (500) on standard HTTP HEAD requests | High | P2 |
| BUG-04 | Decimal input validation is cosmetic only — request still sent to backend | Medium | P2 |
| BUG-05 | Loss of numeric precision for large factorial results (float instead of arbitrary-precision) | Medium | P3 |
| BUG-06 | No protection against rapid repeated clicks (missing debounce/loading state) | Medium | P3 |
| BUG-07 | Pressing Enter does not submit the form | Medium | P3 |
| BUG-08 | No standard security response headers | Medium | P3 |
| BUG-09 | No maximum length validation on the input field | Medium | P3 |

**The one worth explaining in an interview if asked:** BUG-02. It's the most user-impacting one, not because it's the most technically complex, but because it hits a completely valid, in-domain input (1000) with zero feedback — a real user just gets stuck on a static page with no idea what happened. BUG-01 is the same root cause (missing server-side validation) but on inputs that were invalid to begin with; BUG-02 is the same bug reaching a legitimate use case, which is what makes it the one I'd flag as blocking, not just annoying.
