# 671 Books

**671 Books** is a Java-based online bookstore web application. It supports customer and admin logins, book browsing/search, a shopping cart, wishlists, order placement and cancellation, email invoices, and sales/purchase reporting with PDF export and charting.

> This README was generated from the project's existing source files. Update sections marked `TODO` with details specific to your setup (build tool, exact DB schema, deployment target, etc.).

## Features

- **Customer accounts** — registration, login/logout, profile editing (`customerServlet`, `customerDao`)
- **Book catalog** — add/edit/delete books, search by title/author/publisher/genre/year, best sellers & new releases (`bookServlet`, `bookDao`)
- **Shopping cart** — add/remove/update quantity, duplicate-item checks (`cartServlet`, `cartDao`)
- **Wishlist** — add/remove items, move items from wishlist to cart (`wishListServlet`, `wishListDao`)
- **Checkout & payments** — order submission, stock updates, order confirmation emails via Gmail SMTP (`paymentServlet`, `paymentDao`, `SendMailSSL`)
- **Order / shipping management** — view orders, cancel orders with automatic stock restoration, scheduled status updates (`orderServlet`, `orderDao`, `shippingServlet`, `shippingDao`, `ScheduledTask`)
- **Reporting** — purchase/sales reports exported as PDF (iText) and a monthly sales bar chart (JFreeChart) (`reportServlet`, `reportDao`, `GeneratePDF`, `salesChart`)
- **Admin login** — separate admin authentication path in `loginServlet`

## Tech Stack

| Layer      | Technology |
|------------|------------|
| Backend    | Java Servlets (`javax.servlet`) |
| Database   | Oracle DB via JDBC (`oracle.jdbc.driver.OracleDriver`) |
| PDF export | iText (`com.itextpdf`) |
| Charts     | JFreeChart |
| Email      | JavaMail (Gmail SMTP) |
| Frontend   | jQuery (1.3.2 / 1.4.2 / 1.7.2), jQuery UI, custom CSS |

## Project Structure

```
.
├── dao/                     # Data access layer (JDBC)
│   ├── connectionDao.java   # Oracle DB connection factory
│   ├── bookDao.java
│   ├── cartDao.java
│   ├── customerDao.java
│   ├── orderDao.java
│   ├── paymentDao.java
│   ├── reportDao.java
│   ├── shippingDao.java
│   ├── wishListDao.java
│   ├── GeneratePDF.java      # iText PDF report generation
│   ├── salesChart.java       # JFreeChart monthly sales chart
│   ├── SendMailSSL.java      # Email notifications
│   ├── RSA.java               # RSA encryption utility
│   └── ScheduledTask.java     # Background job (order status updates)
├── servlets/                 # Presentation / controller layer
│   ├── loginServlet.java
│   ├── bookServlet.java
│   ├── cartServlet.java
│   ├── wishListServlet.java
│   ├── customerServlet.java
│   ├── orderServlet.java
│   ├── paymentServlet.java
│   ├── reportServlet.java
│   ├── searchServlet.java
│   └── shippingServlet.java
└── static assets
    ├── jquery-1.3.2.js / jquery-1.4.2.min.js / jquery-1.7.2.js
    ├── jquery.paginate.js
    ├── jquery-ui.css
    ├── paginationStyle.css
    ├── style.css
    └── BP-SHPF2.css
```

> `TODO`: If you have JSP views (`*.jsp`), `web.xml`, and a `lib/` folder with the third-party JARs (iText, JFreeChart, JavaMail, ojdbc), add those to the repo and this structure listing.

## Prerequisites

- JDK 8+
- Apache Tomcat (or another Servlet 3.x container)
- Oracle Database (XE or higher)
- Required libraries on the classpath:
  - `ojdbc` (Oracle JDBC driver)
  - `itextpdf`
  - `jfreechart` + `jcommon`
  - `javax.mail`
  - `javax.servlet-api`

## Database Setup

The application expects an Oracle schema with (at minimum) the following tables, inferred from the DAO queries:

- `CUSTOMER` (`c_id`, `cname`, `phone`, `gender`, `email`, `address`, `password`)
- `BOOK` (`isbn`, `title`, `price`, `author`, `publisher`, `publishing_year`, `genre`, `book_qty`, `front_page`)
- `CART` (`isbn`, `c_id`, `c_qty`)
- `WISHLIST` (`c_id`, `isbn`)
- `SHIPPING` (`o_id`, `c_id`, `doi`, `dod`, `status`, ...)
- `SHIP_ITEM` (`o_id`, `isbn`, `o_qty`)

> `TODO`: Export and include the actual DDL / seed scripts if available (e.g. `schema.sql`).

## Configuration

Update the following before running:

1. **Database connection** — `dao/connectionDao.java`
   ```java
   con = DriverManager.getConnection("jdbc:oracle:thin:@<host>:<port>:<SID>", "<user>", "<password>");
   ```
2. **Email credentials** — `dao/SendMailSSL.java` (currently contains hardcoded Gmail credentials — **replace with environment variables or a secrets manager before committing/deploying**).

> ⚠️ **Security note:** The current code contains hardcoded database and email credentials, and builds SQL queries via string concatenation in several DAO methods, which is vulnerable to SQL injection. Before deploying or open-sourcing this project further, it's strongly recommended to:
> - Move credentials to environment variables / a config file excluded via `.gitignore`
> - Replace concatenated SQL with `PreparedStatement` parameter binding throughout
> - Rotate any credentials that have been committed to source control

## Build & Run

> `TODO`: Fill in based on your actual build tool.

**If using a plain WAR / Tomcat setup:**
1. Place required JARs in `WEB-INF/lib`
2. Configure `web.xml` (or rely on `@WebServlet` annotations already present in this codebase)
3. Build a WAR and deploy to Tomcat's `webapps/` directory
4. Start Tomcat and navigate to `http://localhost:8080/<context-path>/home.jsp`

**If using Maven:**
```bash
mvn clean package
cp target/*.war $CATALINA_HOME/webapps/
```
## Acknowledgements

- [iText](https://itextpdf.com/) for PDF generation
- [JFreeChart](https://www.jfree.org/jfreechart/) for charting
- [jQuery](https://jquery.com/) / [jQuery UI](https://jqueryui.com/) for the frontend
