import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PillalasAlVueloSharedModule } from 'app/shared';
import {
  ObservatorioComponent,
  ObservatorioDetailComponent,
  ObservatorioUpdateComponent,
  ObservatorioDeletePopupComponent,
  ObservatorioDeleteDialogComponent,
  observatorioRoute,
  observatorioPopupRoute
} from './';

const ENTITY_STATES = [...observatorioRoute, ...observatorioPopupRoute];

@NgModule({
  imports: [PillalasAlVueloSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ObservatorioComponent,
    ObservatorioDetailComponent,
    ObservatorioUpdateComponent,
    ObservatorioDeleteDialogComponent,
    ObservatorioDeletePopupComponent
  ],
  entryComponents: [
    ObservatorioComponent,
    ObservatorioUpdateComponent,
    ObservatorioDeleteDialogComponent,
    ObservatorioDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillalasAlVueloObservatorioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
