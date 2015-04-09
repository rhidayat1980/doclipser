package com.zenika.docker.ide.ui.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.ILocationProvider;

import com.zenika.docker.api.DockerClient;
import com.zenika.docker.api.DockerClientFactory;

public class DockerBuildLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(ISelection sel, String mode) {
		if (sel instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection) sel).getFirstElement();
			if (selected instanceof IFile) {
				IFile dockerfile = (IFile)selected;
				IPath dockerfilePath = dockerfile.getLocation().removeLastSegments(1);
				launch(dockerfile, dockerfilePath);
			}
		}
	}

	private void launch(final IFile dockerfile, final IPath dockerfilePath) {
		Display.getDefault().asyncExec(
			new Runnable() {
			@Override
			public void run() {
				DockerClient dockerClient = DockerClientFactory.makeDockerClient();
				dockerClient.defaultBuildCommand(dockerfile.getProject().getName(), dockerfilePath.toOSString());
			}
		}
		);
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		IFile dockerfile = (IFile) input.getAdapter(IFile.class);
		IPath dockerfilePath = null;
		if (dockerfile != null) {
			dockerfilePath = dockerfile.getLocation().removeLastSegments(1);
		}
		if (dockerfilePath == null) {
			ILocationProvider locationProvider = (ILocationProvider) input.getAdapter(ILocationProvider.class);
			if (locationProvider != null) {
				dockerfilePath = locationProvider.getPath(input);
			}
		}
		if (dockerfilePath != null) {
			launch(dockerfile, dockerfilePath);
		}
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
