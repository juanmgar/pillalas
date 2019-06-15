/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { AvistamientoDetailComponent } from 'app/entities/avistamiento/avistamiento-detail.component';
import { Avistamiento } from 'app/shared/model/avistamiento.model';

describe('Component Tests', () => {
  describe('Avistamiento Management Detail Component', () => {
    let comp: AvistamientoDetailComponent;
    let fixture: ComponentFixture<AvistamientoDetailComponent>;
    const route = ({ data: of({ avistamiento: new Avistamiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [AvistamientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AvistamientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvistamientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avistamiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
