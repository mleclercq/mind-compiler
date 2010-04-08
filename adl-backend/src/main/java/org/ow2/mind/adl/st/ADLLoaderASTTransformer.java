/**
 * Copyright (C) 2009 STMicroelectronics
 *
 * This file is part of "Mind Compiler" is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact: mind@ow2.org
 *
 * Authors: Matthieu Leclercq
 * Contributors: 
 */

package org.ow2.mind.adl.st;

import static org.ow2.mind.BindingControllerImplHelper.checkItfName;
import static org.ow2.mind.BindingControllerImplHelper.listFcHelper;

import java.util.Map;

import org.objectweb.fractal.adl.ADLException;
import org.objectweb.fractal.adl.AbstractLoader;
import org.objectweb.fractal.adl.Definition;
import org.objectweb.fractal.api.NoSuchInterfaceException;
import org.objectweb.fractal.api.control.IllegalBindingException;
import org.ow2.mind.st.StringTemplateASTTransformer;

/**
 * A simple delegating loader that returns an ADL AST that has been transformed
 * by the {@link StringTemplateASTTransformer}.
 */
public class ADLLoaderASTTransformer extends AbstractLoader {

  // ---------------------------------------------------------------------------
  // Client interfaces
  // ---------------------------------------------------------------------------

  /**
   * The {@link StringTemplateASTTransformer} client interface used by this
   * component to transform ADL AST.
   */
  public StringTemplateASTTransformer astTransformerItf;

  // ---------------------------------------------------------------------------
  // Implementation of the IDLLoader interface
  // ---------------------------------------------------------------------------

  public Definition load(final String name, final Map<Object, Object> context)
      throws ADLException {
    final Definition d = clientLoader.load(name, context);
    return astTransformerItf.toStringTemplateAST(d);
  }

  // ---------------------------------------------------------------------------
  // Overridden BindingController methods
  // ---------------------------------------------------------------------------

  @Override
  public void bindFc(final String itfName, final Object value)
      throws NoSuchInterfaceException, IllegalBindingException {
    checkItfName(itfName);

    if (itfName.equals(StringTemplateASTTransformer.ITF_NAME)) {
      astTransformerItf = (StringTemplateASTTransformer) value;
    } else {
      super.bindFc(itfName, value);
    }
  }

  @Override
  public String[] listFc() {
    return listFcHelper(super.listFc(), StringTemplateASTTransformer.ITF_NAME);
  }

  @Override
  public Object lookupFc(final String itfName) throws NoSuchInterfaceException {
    checkItfName(itfName);

    if (itfName.equals(StringTemplateASTTransformer.ITF_NAME)) {
      return astTransformerItf;
    } else {
      return super.lookupFc(itfName);
    }
  }

  @Override
  public void unbindFc(final String itfName) throws NoSuchInterfaceException,
      IllegalBindingException {
    checkItfName(itfName);

    if (itfName.equals(StringTemplateASTTransformer.ITF_NAME)) {
      astTransformerItf = null;
    } else {
      super.unbindFc(itfName);
    }
  }
}