Android Basics
=============
**Android Basics ** is a predefined structure which helps in rapid development.

```java
// USAGE.

public class YourActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		// YOUR LOGIC GOES HERE
	}
}

 ```
 ```java
 // Database Initialize
 // Database has functions which return data in HashMap<String,String>.
 // USAGE this.db.setTableName("user").select().from().where("id = 1").executeQuery();
 // USAGE this.db.setTableName("user").find();
 // USAGE this.db.setTableName("user").findFirst("id = 1");
 public class YourActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		initDb();
	}
	
	// AFTER SPLASH ACTIVITY CALL initDb once. the destroy method destorys the object automatically.
	
}
 ```