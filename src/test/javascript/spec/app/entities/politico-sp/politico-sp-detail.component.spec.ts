/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoSpDetailComponent } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp-detail.component';
import { PoliticoSpService } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.service';
import { PoliticoSp } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.model';

describe('Component Tests', () => {

    describe('PoliticoSp Management Detail Component', () => {
        let comp: PoliticoSpDetailComponent;
        let fixture: ComponentFixture<PoliticoSpDetailComponent>;
        let service: PoliticoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoSpDetailComponent],
                providers: [
                    PoliticoSpService
                ]
            })
            .overrideTemplate(PoliticoSpDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoSpDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PoliticoSp(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.politico).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
