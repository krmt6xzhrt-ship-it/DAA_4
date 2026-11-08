import csv
from collections import defaultdict
from statistics import median
from pathlib import Path
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

rows=list(csv.DictReader(open('results/results.csv',encoding='utf-8')))
groups=defaultdict(list)
for row in rows:
    groups[row['algorithm'],row['input'],int(row['n'])].append(row)

def curve(ax,method,kind,field):
    keys=sorted([key for key in groups if key[0]==method and key[1]==kind],key=lambda k:k[2])
    x=[key[2] for key in keys]
    y=[median(float(row[field]) for row in groups[key]) for key in keys]
    ax.plot(x,y,marker='o',label=f'{method} / {kind}')

out=Path('results/plots')
fig,ax=plt.subplots(figsize=(8,5))
for kind in ('random','disjoint'):curve(ax,'Recursive',kind,'operations')
ax.set(yscale='log',title='Recursive Edit Distance: number of calls',xlabel='String length n (characters per string)',ylabel='Recursive calls (count)')
ax.legend();ax.grid(alpha=.3);fig.tight_layout();fig.savefig(out/'01_recursive_calls.png',dpi=170);plt.close(fig)
fig,ax=plt.subplots(figsize=(8,5))
for method in ('Table','TwoRows'):
    for kind in ('random','disjoint'):curve(ax,method,kind,'time_ms')
ax.set(title='Dynamic programming: median running time',xlabel='String length n (characters per string)',ylabel='Median time (ms)')
ax.legend();ax.grid(alpha=.3);fig.tight_layout();fig.savefig(out/'02_dp_time.png',dpi=170);plt.close(fig)
fig,ax=plt.subplots(figsize=(8,5))
for method in ('Naive','KMP'):
    for kind in ('random','repetitive'):curve(ax,method,kind,'operations')
ax.set(title='String search: measured character comparisons',xlabel='Text length n (characters)',ylabel='Character comparisons (count)')
ax.legend();ax.grid(alpha=.3);fig.tight_layout();fig.savefig(out/'03_search_comparisons.png',dpi=170);plt.close(fig)
fig,ax=plt.subplots(figsize=(8,5))
for kind in ('random','repetitive'):
    keys=sorted(k for k in groups if k[0]=='Naive' and k[1]==kind)
    x=[k[2] for k in keys]
    y=[median(int(r['operations']) for r in groups['Naive',kind,n])/median(int(r['operations']) for r in groups['KMP',kind,n]) for n in x]
    ax.plot(x,y,marker='o',label=kind)
ax.axhline(1,linestyle='--',linewidth=1)
ax.set(title='Comparison ratio: Naive / KMP',xlabel='Text length n (characters)',ylabel='Comparison ratio (times)')
ax.legend();ax.grid(alpha=.3);fig.tight_layout();fig.savefig(out/'04_search_ratio.png',dpi=170);plt.close(fig)
print('created 4 plots')
