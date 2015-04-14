package com.zenika.docker.ide.ui.launch;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import com.zenika.docker.api.DockerClient;
import com.zenika.docker.api.DockerClientFactory;

public class DockerPsLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(ISelection sel, String mode) {
		launch();
	}

	private void launch() {
		Job job = new Job("Docker Build Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				DockerClient dockerClient = DockerClientFactory
						.makeDockerClient();
				dockerClient.defaultPsCommand();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		launch();
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		// TODO Auto-generated method stub
		return null;
	}

}
