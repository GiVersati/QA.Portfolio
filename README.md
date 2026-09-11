
 Geovanna Versati — QA Portfolio

### About me 👋

Hi, I'm Geovanna — a QA Engineer with 3 years of experience across manual and automated testing, working on Web, API, and Mobile applications in Agile/Scrum environments. I've worked on systems where quality really matters, including a critical aviation platform for GOL. My background in JavaScript, React, and Node.js lets me dig into root cause analysis and speak the same language as the developers I work with, not just report symptoms.

I'm certified in AWS Cloud Practitioner and Quality Assurance (Alura, with Cypress.io focus), and I hold an advanced English certificate from CCBEU. Currently open to QA / SDET / QA Automation opportunities, remote — Brazil or international.

📩 geovannaversati@gmail.com | [GitHub](https://github.com/GiVersati) | [LinkedIn](https://www.linkedin.com/in/geovannaversati)

---

### My experience 🏢

**QA Engineer — CI&T** · Remote
QA on an agile squad, involved across the full end-to-end development cycle. Manual and API testing (Postman), automated API testing, and mobile testing. Test scenario design, manual and automated with Cypress. Data testing involving Kafka and offsets. Bug reporting and documentation in Jira and TestRail.

**Analista de QA — Atomic Solutions** · Remote
QA on an aviation systems project for GOL. Automated regression testing with Selenium WebDriver integrated with Jenkins. API testing with Postman and MySQL validation. Automated script maintenance, exploratory testing, and automated mobile testing. Test suite creation and execution with documentation, reporting via Azure DevOps.

**Analista de QA — Yaman** · Short-term, PJ contract
Manual, API, and mobile testing. Web testing with Java and Selenium; mobile testing with Robot Framework.

**Analista de teste/QA — Pris** · Remote
Manual, automated, and API testing using Cypress, Postman, Swagger, Azure DevOps, and BDD.

**Frontend Developer — Aikon Sistemas**
Front-end development with JavaScript, React, Node.js, and Python, also working on the backend with a database. This experience on the development side is what lets me do real root-cause analysis today, not just report "it's broken."

---

### 📱 Mobile Testing — a closer look

Mobile is a core part of my QA work, not a side skill:

- **Devices and environment:** I've worked with a mix of real devices and emulators, depending on what each company provided — Android Studio's emulator, and Xcode's iOS Simulator when available. The usual flow: the dev team provides a homolog build, I download it, and test directly on device — catching layout breaks and touch-interaction issues that never show up in an emulator. When needed to stay within scope and meet a deadline, I've tested on my own personal Android device.
- **Android automation with Appium:** hands-on experience setting up the test environment with Android Studio, automating with Java (the usual company standard) and also personally with JavaScript.
- **iOS:** no hands-on automation yet — companies rarely provide a MacBook for testing, which limits real device access more than Android. I've participated in meetings covering the Appium/XCUITest setup for iOS and seen it applied in practice, so I understand the approach even without direct hands-on time.
- **Why mobile tests are flakier than web, and how I handle it:** device and OS-version fragmentation is the main source — an element that loads fine on one screen size shifts position on another. I address this with explicit waits tied to real conditions instead of fixed sleeps, plus retry logic for network-dependent steps.
- **Code samples:** [`/mobile-automation-appium`](./mobile-automation-appium) — Appium + Java + TestNG + Maven, with two examples (Calculator and a Wi-Fi settings toggle) covering two different interaction patterns, plus my [notes on how iOS automation would work](./mobile-automation-appium/ios-approach.md) based on setup discussions I've been part of.

---

### 🗺️ Geospatial / ArcGIS Testing — conceptual understanding

I haven't worked with ArcGIS directly, but I understand the QA problem it represents, and I want to be upfront about that distinction — this section shows how I'd *approach* it, not claimed hands-on tool experience.

- **CAD → ArcGIS Indoors conversion validation** is fundamentally a data integrity problem — the same category of validation I already do with SQL and Kafka, applied to geospatial data instead of relational data.
- **Geometry-level checks:** unclosed shapes, self-intersecting lines.
- **Attribute-level checks:** missing or duplicate unit (room) names, missing metadata.
- **Spatial accuracy:** whether coordinates and scale were preserved correctly during conversion — a shifted or distorted floor plan breaks indoor navigation.
- **Indoor navigation testing:** I think of this as graph connectivity testing — every unit needs at least one pathway connected to an exit; a disconnected pathway makes a room unreachable by the routing engine.
- **How I'd investigate an intermittent navigation bug:** start at the source CAD data, then check conversion logs, then the routing layer — the same root-cause, source-outward approach I use investigating data issues in Kafka pipelines.

**Mini case study — how I'd design an automated data-integrity check for a converted floor plan:**

```python
# Illustrative validation logic, not tied to a specific ArcGIS API —
# shows how I'd translate the manual checks above into an automated
# regression check, the same way I validate data integrity elsewhere.

def validate_floor_plan(units, pathways):
    issues = []

    # Attribute-level: every unit needs a unique, non-empty name
    seen_names = set()
    for unit in units:
        if not unit["name"]:
            issues.append(f"Unit {unit['id']} has a missing name")
        elif unit["name"] in seen_names:
            issues.append(f"Duplicate unit name: {unit['name']}")
        seen_names.add(unit["name"])

    # Geometry-level: shape must be closed (first point == last point)
    for unit in units:
        coords = unit["geometry"]
        if coords[0] != coords[-1]:
            issues.append(f"Unit {unit['id']} has an unclosed shape")

    # Navigation-level: every unit must connect to at least one pathway
    connected_units = {p["from_unit"] for p in pathways} | {p["to_unit"] for p in pathways}
    for unit in units:
        if unit["id"] not in connected_units:
            issues.append(f"Unit {unit['id']} has no connected pathway — unreachable")

    return issues
```

This is the kind of check I'd propose running automatically on every CAD import, the same way I'd add a regression check for any recurring data-integrity risk — catching it before manual QA even starts, not instead of it.

---

### 📋 Sample Test Report — Independent Testing Practice

To keep my testing skills sharp between projects, I ran a full independent QA cycle on a public calculator application, applying the same rigor I'd use on production work:

- **Scope:** functional, API, security, and mobile responsiveness testing
- **Techniques:** exploratory testing, equivalence partitioning, boundary value analysis, negative testing, direct API testing via curl (bypassing the UI), basic security header review, cross-referencing results against independent reference calculators
- **Coverage:** 23 test cases executed (100% of planned scope)
- **Result:** 9 defects found and documented — 2 Critical, 1 High, 6 Medium — each with severity, priority, reproduction steps, expected vs. actual result, and screenshot evidence
- **Standout finding:** confirmed, via direct API requests bypassing the front-end, that invalid input reaches the backend with no server-side validation — triggering unhandled 500 errors instead of clean 400 responses, including for a **silent failure on a valid input (1000)** that a real user would hit with zero explanation on screen

Full report (test case table, defect log, metrics, and recommendations): [`QA_Test_Report_Factorial_Calculator.docx`](./QA_Test_Report_Factorial_Calculator.docx)
Test cases and defects on their own, without opening the doc: [`factorial-calculator-test-cases.md`](./factorial-calculator-test-cases.md)

---

### Tools 🔧

- **Automation:** [Cypress](https://www.cypress.io/), [Selenium WebDriver](https://www.selenium.dev/), [Appium](https://appium.io/) (Android), [Robot Framework](https://robotframework.org/)
- **API testing:** [Postman](https://www.postman.com/), [Swagger](https://swagger.io/)
- **CI/CD:** [Jenkins](https://www.jenkins.io/), [Azure DevOps](https://azure.microsoft.com/en-us/products/devops)
- **Data & messaging:** [Kafka](https://kafka.apache.org/), MySQL
- **Bug tracking & docs:** [Jira](https://www.atlassian.com/software/jira), [TestRail](https://www.testrail.com/)
- **Methodology:** BDD/Gherkin, Agile/Scrum
- **AI-assisted testing:** using LLMs (Claude and similar) to accelerate test scenario generation and support automation scripts — always reviewing outputs critically, since AI generates plausible scenarios that don't always cover real business rules.

### Tech skills 💻

**SQL**

| Syntax & execution | Comparison | Logical | Aggregation |
|---|---|---|---|
| SELECT, FROM, WHERE | =, !=, <, > | AND, OR, NOT | COUNT(), SUM() |
| GROUP BY, ORDER BY | BETWEEN, IN | LIKE | MIN(), MAX() |
| JOIN | IS NULL | | |

- REST API testing and validation
- Manual, functional, regression, smoke, and exploratory testing
- Test case creation and defect documentation
- Root cause analysis
- Java (Selenium context), JavaScript, HTML/CSS

### Soft skills 📁

- Cross-functional communication with development teams
- Critical evaluation of AI-generated test output
- Adaptability across Web, API, and Mobile testing layers
- Prioritization under sprint deadlines

### Certifications 🏆

- AWS Certified Cloud Practitioner — Amazon Web Services
- Quality Assurance — Alura (Cypress.io + 29 related competencies)
- Engenharia de Dados — Alura
- English — CCBEU (Centro Cultural Brasil-Estados Unidos)


*Open to remote opportunities — Brazil & International.*
-------------------------------------------------------
