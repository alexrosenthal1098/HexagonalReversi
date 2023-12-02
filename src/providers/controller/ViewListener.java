package providers.controller;

/**
 * this interface is used to represent what events will be listined to from the view.
 */
public interface ViewListener {

  /**
   * this method is used to listen to the view events.
   */
  void onViewEvent(IEvent event);
}
