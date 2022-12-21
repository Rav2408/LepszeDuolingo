package pl.edu.pb.lepszeduolingo.db;

public interface ResponseListener {
    public void onEventCompleted();
    public void onEventFailed();
}
