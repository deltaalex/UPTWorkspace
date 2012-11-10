package test_erase_me;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;

public abstract class ProgressMonitorWrapper implements IProgressMonitor, IProgressMonitorWithBlocking {

	/** The wrapped progress monitor. */
	private IProgressMonitor progressMonitor;

	/** 
	 * Creates a new wrapper around the given monitor.
	 *
	 * @param monitor the progress monitor to forward to
	 */
	protected ProgressMonitorWrapper(IProgressMonitor monitor) {
		Assert.isNotNull(monitor);
		progressMonitor = monitor;
	}

	/** 
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#beginTask(String, int)
	 */
	public void beginTask(String name, int totalWork) {
		progressMonitor.beginTask(name, totalWork);
	}

	/**
	 * This implementation of a <code>IProgressMonitorWithBlocking
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitorWithBlocking#clearBlocked()
	 * @since 3.0
	 */
	public void clearBlocked() {
		if (progressMonitor instanceof IProgressMonitorWithBlocking)
			((IProgressMonitorWithBlocking) progressMonitor).clearBlocked();
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#done()
	 */
	public void done() {
		progressMonitor.done();
	}

	/**
	 * Returns the wrapped progress monitor.
	 *
	 * @return the wrapped progress monitor
	 */
	public IProgressMonitor getWrappedProgressMonitor() {
		return progressMonitor;
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#internalWorked(double)
	 */
	public void internalWorked(double work) {
		progressMonitor.internalWorked(work);
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#isCanceled()
	 */
	public boolean isCanceled() {
		return progressMonitor.isCanceled();
	}

	/**
	 * This implementation of a <code>IProgressMonitorWithBlocking
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitorWithBlocking#setBlocked(IStatus)
	 * @since 3.0
	 */
	public void setBlocked(IStatus reason) {
		if (progressMonitor instanceof IProgressMonitorWithBlocking)
			((IProgressMonitorWithBlocking) progressMonitor).setBlocked(reason);
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#setCanceled(boolean)
	 */
	public void setCanceled(boolean b) {
		progressMonitor.setCanceled(b);
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#setTaskName(String)
	 */
	public void setTaskName(String name) {
		progressMonitor.setTaskName(name);
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#subTask(String)
	 */
	public void subTask(String name) {
		progressMonitor.subTask(name);
	}

	/**
	 * This implementation of a <code>IProgressMonitor
	 * method forwards to the wrapped progress monitor.
	 * Clients may override this method to do additional
	 * processing.
	 *
	 * @see IProgressMonitor#worked(int)
	 */
	public void worked(int work) {
		progressMonitor.worked(work);
	}	
}
