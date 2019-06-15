/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { ObservatorioDetailComponent } from 'app/entities/observatorio/observatorio-detail.component';
import { Observatorio } from 'app/shared/model/observatorio.model';

describe('Component Tests', () => {
  describe('Observatorio Management Detail Component', () => {
    let comp: ObservatorioDetailComponent;
    let fixture: ComponentFixture<ObservatorioDetailComponent>;
    const route = ({ data: of({ observatorio: new Observatorio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [ObservatorioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ObservatorioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservatorioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.observatorio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
