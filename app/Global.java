import com.typesafe.conductr.bundlelib.play.ConnectionContext;
import com.typesafe.conductr.bundlelib.play.StatusService;
import play.Application;
import play.GlobalSettings;
import play.libs.HttpExecution;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        super.onStart(application);

        // Signal to ConductR that app is ready
        ConnectionContext cc = ConnectionContext.create(HttpExecution.defaultContext());
        StatusService.getInstance().signalStartedOrExit(cc);
    }
}
