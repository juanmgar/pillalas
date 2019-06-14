import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PillalasAlVueloSharedLibsModule, PillalasAlVueloSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PillalasAlVueloSharedLibsModule, PillalasAlVueloSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PillalasAlVueloSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillalasAlVueloSharedModule {
  static forRoot() {
    return {
      ngModule: PillalasAlVueloSharedModule
    };
  }
}
