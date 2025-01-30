<div align= "center">
    <img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&height=180&text=RiQ_Player%20(music%20player%20GUI%20for%20.wav)&animation=&fontColor=ffffff&fontSize=40" />
    </div>

## 📌 화면 미리보기
- [시연 영상 보러가기](https://youtube.com/shorts/9QMgtPa1c8c)
<p align="center">
  <img src="https://github.com/user-attachments/assets/ca2ee444-ca53-4fca-b544-3890b460d5ab">
  <img src="https://github.com/user-attachments/assets/966cc3b2-fcf4-40ea-8037-2cd4f7a9bd28">
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/223282d5-4eda-4c20-8657-adadac8a9f7b">
  <img src="https://github.com/user-attachments/assets/a9754faf-c005-4d75-9c90-e80339458bde">
</p>

## 🎵 RiQ_Player
- .wav 형식만 지원되는 GUI 음악 플레이어입니다.

## 🛠️ Tech Stacks
<div style="margin: ; text-align: left;" "text-align: left;"> <img src="https://img.shields.io/badge/Java-007396?style=plastic&logo=Java&logoColor=white">
          </div>
    
## 📕 Use Liberaries
- JavaSE-11
- [jsoup-1.18.3.jar](https://jsoup.org/download) <br>
- [aspose-imageing-24.12-jdk16.jar](https://releases.aspose.com/imaging/java/24-12/)

## 🎛️ Main Features   
- .wav 파일 재생, 일시정지
- 플레이 리스트 저장
- 재생 목록에 파일 드롭을 통한 복사 및 추가
- 랜덤 셔플 및 직접 배치
- 음악 시간 설정 및 볼륨 컨트롤

## 🎮 조작 방법  
<details><summary>1. 기본 창 조작 (클릭하여 내용보기)
</summary>
- ◀◀ :  이전 곡 (PageUp)<br>
- ▶▶ : 다음 곡 (PageDown)<br>
- ▶ / | | : 재생 / 일시정지 (Space Bar)<br>
- ∝ : 셔플 (R)<br>
- ≡ : 재생 목록 (L)<br>
- 재생 시간 +- 5초 : 방향키 ←(-5), →(+5)<br>
- 재생 시간 타임라인 클릭 앤 드래그로 원하는 시간대로 이동 가능<br>
- 볼륨 조절 +- : 방향키 ↑(+), ↓(-)<br>
- 볼륨 조절 타임라인 클릭 앤 드래그로 원하는 볼륨크기 설정 가능
</details>
<details><summary>2. 재생 목록 조작 (클릭하여 내용보기)
</summary>
- 재생 목록 이용 시에도 기본 창 조작 이용 가능<br>
- Insert : music 폴더에 저장된 파일 중 재생목록에 없는 모든 파일 추가<br>
- Delete : 선택된 음원 재생 목록에서 제거 (실제 파일은 유지)<br>
- Home : 선택된 음원 재생 목록에서 1칸 올리기<br>
- End : 선택된 음원 재생 목록에서 1칸 내리기
</details>

## ⚠️ Warning 
- 음원 파일 이름이 (아티스트 - 제목.wav) 형식이 아니면 제대로 표시되지 않습니다.
- 동일한 음원 파일 이름이 존재할 경우 추가되지 않습니다.
- 재생 목록에서 곡을 삭제해도 실제 폴더 내 파일은 삭제되지 않습니다.
- 파일 이름을 통해 이미지를 웹에서 검색해 받아오기 때문에 시간 초과 및 정확성 이슈가 생길 수 있습니다.
- 이미지 검색은 apple music 사이트의 소스를 이용해 받아오기에, 해당 사이트의 변동 등으로 오류가 생길 수 있습니다.
- 현재 플레이 리스트는 1개만 저장되어 사용됩니다.
- 볼륨 컨트롤의 경우 Gain을 이용하여 임의 설정하였기에 보여지는 볼륨 타임라인 비율과 소리 조절이 다를 수 있습니다.
