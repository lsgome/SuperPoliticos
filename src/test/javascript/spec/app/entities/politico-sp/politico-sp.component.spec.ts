/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoSpComponent } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.component';
import { PoliticoSpService } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.service';
import { PoliticoSp } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.model';

describe('Component Tests', () => {

    describe('PoliticoSp Management Component', () => {
        let comp: PoliticoSpComponent;
        let fixture: ComponentFixture<PoliticoSpComponent>;
        let service: PoliticoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoSpComponent],
                providers: [
                    PoliticoSpService
                ]
            })
            .overrideTemplate(PoliticoSpComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoSpComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PoliticoSp(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.politicos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
