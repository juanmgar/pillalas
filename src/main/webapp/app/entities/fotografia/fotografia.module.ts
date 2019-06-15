import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PillalasAlVueloSharedModule } from 'app/shared';
import {
  FotografiaComponent,
  FotografiaDetailComponent,
  FotografiaUpdateComponent,
  FotografiaDeletePopupComponent,
  FotografiaDeleteDialogComponent,
  fotografiaRoute,
  fotografiaPopupRoute
} from './';

const ENTITY_STATES = [...fotografiaRoute, ...fotografiaPopupRoute];

@NgModule({
  imports: [PillalasAlVueloSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FotografiaComponent,
    FotografiaDetailComponent,
    FotografiaUpdateComponent,
    FotografiaDeleteDialogComponent,
    FotografiaDeletePopupComponent
  ],
  entryComponents: [FotografiaComponent, FotografiaUpdateComponent, FotografiaDeleteDialogComponent, FotografiaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillalasAlVueloFotografiaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
