import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PillalasAlVueloSharedModule } from 'app/shared';
import { AgmCoreModule } from '@agm/core';
import {
  AvistamientoComponent,
  AvistamientoDetailComponent,
  AvistamientoUpdateComponent,
  AvistamientoDeletePopupComponent,
  AvistamientoDeleteDialogComponent,
  avistamientoRoute,
  avistamientoPopupRoute
} from './';

const ENTITY_STATES = [...avistamientoRoute, ...avistamientoPopupRoute];

@NgModule({
  imports: [
    PillalasAlVueloSharedModule,
    RouterModule.forChild(ENTITY_STATES),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyA3oNg0Hg98Mscqm-9I-HxTV-q-Wu7vMTI'
    })
  ],
  declarations: [
    AvistamientoComponent,
    AvistamientoDetailComponent,
    AvistamientoUpdateComponent,
    AvistamientoDeleteDialogComponent,
    AvistamientoDeletePopupComponent
  ],
  entryComponents: [
    AvistamientoComponent,
    AvistamientoUpdateComponent,
    AvistamientoDeleteDialogComponent,
    AvistamientoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillalasAlVueloAvistamientoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
